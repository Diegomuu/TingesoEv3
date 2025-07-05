import React, { useState, useEffect } from 'react';
import Navbar from './Navbar';
import './Reportes.css';

const Reportes = () => {
  const [reporteData, setReporteData] = useState({});
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [fechaInicio, setFechaInicio] = useState(new Date().getFullYear() + '-01'); // Formato YYYY-MM
  const [fechaFin, setFechaFin] = useState(new Date().getFullYear() + '-12');

  const obtenerMeses = (inicio, fin) => {
    const meses = [];
    const fechaInicio = new Date(inicio + '-01');
    const fechaFin = new Date(fin + '-01');
    
    let mesActual = new Date(fechaInicio);
    
    while (mesActual <= fechaFin) {
      meses.push(new Date(mesActual));
      mesActual.setMonth(mesActual.getMonth() + 1);
    }
    
    return meses;
  };

  const nombreMes = (fecha) => {
    return fecha.toLocaleString('es-ES', { month: 'long' });
  };

  const fetchReportes = async () => {
    setLoading(true);
    setError(null);
    try {
      const inicio = fechaInicio + '-01'; // Primer día del mes
      const fin = fechaFin + '-31'; // Último día del mes (aproximado)

      const response = await fetch(
        `${import.meta.env.VITE_BASE}/reservas/fecha-entre?inicio=${inicio}&fin=${fin}`
      );

      if (!response.ok) {
        throw new Error('Error al obtener los datos de reservas');
      }

      const reservas = await response.json();
      const meses = obtenerMeses(fechaInicio, fechaFin);
      const reporteTemp = {};

      // Inicializar el objeto de reporte con todos los meses en 0
      meses.forEach(mes => {
        const nombreMesActual = nombreMes(mes);
        reporteTemp[nombreMesActual] = { '10': 0, '15': 0, '20': 0 };
      });

      // Procesar las reservas
      reservas.forEach(reserva => {
        const fecha = new Date(reserva.fechaReserva);
        const mesNombre = nombreMes(fecha);
        const vueltas = reserva.vueltas.toString();
        const monto = reserva.montoTotal;

        if (reporteTemp[mesNombre] && (vueltas === '10' || vueltas === '15' || vueltas === '20')) {
          reporteTemp[mesNombre][vueltas] += monto;
        }
      });

      setReporteData(reporteTemp);
    } catch (error) {
      console.error('Error:', error);
      setError('Error al cargar los datos del reporte');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchReportes();
  }, [fechaInicio, fechaFin]);

  const calcularTotalPorMes = (mes) => {
    return Object.values(reporteData[mes] || {}).reduce((acc, curr) => acc + curr, 0);
  };

  const calcularTotalPorVueltas = (vueltas) => {
    return Object.keys(reporteData).reduce((acc, mes) => acc + (reporteData[mes][vueltas] || 0), 0);
  };

  const calcularTotalGeneral = () => {
    return Object.keys(reporteData).reduce((acc, mes) => acc + calcularTotalPorMes(mes), 0);
  };

  const handleFechaInicioChange = (e) => {
    const nuevaFecha = e.target.value;
    if (nuevaFecha > fechaFin) {
      setError('La fecha de inicio no puede ser posterior a la fecha fin');
      return;
    }
    setFechaInicio(nuevaFecha);
    setError(null);
  };

  const handleFechaFinChange = (e) => {
    const nuevaFecha = e.target.value;
    if (nuevaFecha < fechaInicio) {
      setError('La fecha fin no puede ser anterior a la fecha de inicio');
      return;
    }
    setFechaFin(nuevaFecha);
    setError(null);
  };

  if (loading) return <div className="loading">Cargando reportes...</div>;

  const meses = obtenerMeses(fechaInicio, fechaFin);

  return (
    <div className="reportes-container">
      <Navbar />
      <div className="reportes-content">
        <h1>Reporte de Ingresos por Vueltas</h1>
        
        <div className="fecha-selector-container">
          <div className="fecha-selector-group">
            <label htmlFor="fecha-inicio">Desde:</label>
            <input
              type="month"
              id="fecha-inicio"
              value={fechaInicio}
              onChange={handleFechaInicioChange}
              max={fechaFin}
            />
          </div>
          <div className="fecha-selector-group">
            <label htmlFor="fecha-fin">Hasta:</label>
            <input
              type="month"
              id="fecha-fin"
              value={fechaFin}
              onChange={handleFechaFinChange}
              min={fechaInicio}
            />
          </div>
        </div>

        {error && <div className="error">{error}</div>}

        <div className="table-container">
          <table className="reportes-table">
            <thead>
              <tr>
                <th>Número de vueltas o tiempo máximo permitido</th>
                {meses.map(mes => (
                  <th key={nombreMes(mes)}>{nombreMes(mes)}</th>
                ))}
                <th>TOTAL</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>10 vueltas o máx 10 min</td>
                {meses.map(mes => (
                  <td key={nombreMes(mes)}>
                    ${(reporteData[nombreMes(mes)]?.['10'] || 0).toLocaleString()}
                  </td>
                ))}
                <td>${calcularTotalPorVueltas('10').toLocaleString()}</td>
              </tr>
              <tr>
                <td>15 vueltas o máx 15 min</td>
                {meses.map(mes => (
                  <td key={nombreMes(mes)}>
                    ${(reporteData[nombreMes(mes)]?.['15'] || 0).toLocaleString()}
                  </td>
                ))}
                <td>${calcularTotalPorVueltas('15').toLocaleString()}</td>
              </tr>
              <tr>
                <td>20 vueltas o máx 20 min</td>
                {meses.map(mes => (
                  <td key={nombreMes(mes)}>
                    ${(reporteData[nombreMes(mes)]?.['20'] || 0).toLocaleString()}
                  </td>
                ))}
                <td>${calcularTotalPorVueltas('20').toLocaleString()}</td>
              </tr>
              <tr className="total-row">
                <td>TOTAL</td>
                {meses.map(mes => (
                  <td key={nombreMes(mes)}>
                    ${calcularTotalPorMes(nombreMes(mes)).toLocaleString()}
                  </td>
                ))}
                <td>${calcularTotalGeneral().toLocaleString()}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default Reportes; 