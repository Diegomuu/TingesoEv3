import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import Navbar from './Navbar'; // ✅ Importamos el Navbar
import "../assets/styles.css";
import "./Tarifas.css";

const Tarifas = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { clientes, cantidadPersonas } = location.state || { clientes: [], cantidadPersonas: 0 };

  const [tarifas, setTarifas] = useState([]);
  const [vueltas, setVueltas] = useState(10);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [reservaData, setReservaData] = useState(null);
  const [fechaReserva, setFechaReserva] = useState(() => {
    // Por defecto, hoy
    const hoy = new Date();
    return hoy.toISOString().split('T')[0];
  });
  const [horaReserva, setHoraReserva] = useState(() => {
    // Por defecto, hora actual redondeada a la siguiente hora
    const ahora = new Date();
    ahora.setMinutes(0, 0, 0);
    return ahora.toTimeString().slice(0,5);
  });

  // Preparar los datos de clientes para la API
  const prepararDatosClientes = () => {
    return clientes.map(cliente => {
      if (!cliente.id) {
        console.error(`Cliente ${cliente.nombre} no tiene ID`);
        throw new Error(`Error: Cliente ${cliente.nombre} no tiene ID asignado`);
      }
      return {
        id: cliente.id,
        fechaNacimiento: cliente.cumpleanos
      };
    });
  };

  const calcularTarifas = async () => {
    if (!clientes || clientes.length === 0) {
      setError("No hay clientes registrados para calcular la tarifa.");
      return;
    }

    setLoading(true);
    setError(null);

    try {
      // Usar fechaReserva seleccionada
      const payload = {
        clientes: prepararDatosClientes(),
        cantidadPersonas: cantidadPersonas,
        vueltas: vueltas,
        fechaReserva: fechaReserva // <-- aquí
      };

      console.log('Enviando payload:', payload);

      const response = await fetch(`${import.meta.env.VITE_BASE}/tarifas/calcular`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });

      if (!response.ok) {
        throw new Error("Error al calcular las tarifas");
      }

      const tarifasCalculadas = await response.json();
      console.log('Tarifas calculadas:', tarifasCalculadas);

      // Combinar las tarifas con la información de los clientes
      const tarifasConDetalles = tarifasCalculadas.map((tarifa, index) => ({
        nombre: clientes[index].nombre,
        monto: tarifa,
        fechaNacimiento: clientes[index].cumpleanos
      }));

      setTarifas(tarifasConDetalles);
    } catch (error) {
      console.error("Error:", error);
      setError("Hubo un problema al calcular las tarifas. Inténtalo de nuevo.");
    } finally {
      setLoading(false);
    }
  };

  const crearReserva = async () => {
    try {
      const montoTotal = tarifas.reduce((total, tarifa) => total + tarifa.monto, 0);
      // Usar fechaReserva y horaReserva seleccionadas
      const payload = {
        fechaReserva,
        horaReserva,
        vueltas,
        cantidadPersonas,
        nombreReservante: clientes[0].nombre,
        clienteIds: clientes.map(cliente => cliente.id),
        montoTotal
      };

      console.log('Creando reserva con payload:', payload);

      const response = await fetch(`${import.meta.env.VITE_BASE}/reservas/crear`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
      });

      if (!response.ok) {
        throw new Error('Error al crear la reserva');
      }

      const reservaCreada = await response.json();
      console.log('Reserva creada:', reservaCreada);
      return reservaCreada;
    } catch (error) {
      console.error('Error al crear reserva:', error);
      throw error;
    }
  };

  const irAPagar = async () => {
    try {
      setLoading(true);
      setError(null);
      
      const reservaCreada = await crearReserva();
      const montoTotal = tarifas.reduce((total, tarifa) => total + tarifa.monto, 0);
      
      navigate('/pagos', { 
        state: { 
          tarifas, 
          montoTotal,
          vueltas,
          cantidadPersonas,
          reserva: reservaCreada // Pasamos los datos de la reserva creada
        } 
      });
    } catch (error) {
      setError('Error al crear la reserva: ' + error.message);
      console.error('Error en el proceso de pago:', error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (clientes.length > 0 && tarifas.length === 0) {
      calcularTarifas();
    }
  }, [clientes, vueltas]);

  return (
    <div className="tarifas-container">
      <Navbar />
      <div className="tarifas-content">
        <h1>Cálculo de Tarifas</h1>
        <div className="info-section">
          <p>Cantidad de personas: {cantidadPersonas}</p>
          <div className="fecha-hora-selector">
            <label>
              Fecha de la reserva:
              <input
                type="date"
                value={fechaReserva}
                onChange={e => setFechaReserva(e.target.value)}
                min={new Date().toISOString().split('T')[0]}
                disabled={loading}
              />
            </label>
            <label>
              Hora de la reserva:
              <input
                type="time"
                value={horaReserva}
                onChange={e => setHoraReserva(e.target.value)}
                disabled={loading}
              />
            </label>
          </div>
          <div className="vueltas-selector">
            <label htmlFor="vueltas">Número de vueltas:</label>
            <select
              id="vueltas"
              value={vueltas}
              onChange={(e) => setVueltas(parseInt(e.target.value, 10))}
              disabled={loading}
            >
              <option value={10}>10 vueltas</option>
              <option value={15}>15 vueltas</option>
              <option value={20}>20 vueltas</option>
            </select>
            <button 
              onClick={calcularTarifas}
              disabled={loading}
              className="btn-calcular"
            >
              {loading ? 'Calculando...' : 'Recalcular Tarifas'}
            </button>
          </div>
        </div>

        {error && <div className="error-message">{error}</div>}

        <div className="tarifas-grid">
          {tarifas.map((tarifa, index) => (
            <div key={index} className="tarifa-card">
              <h3>{tarifa.nombre}</h3>
              <div className="tarifa-details">
                <p className="monto">$ {tarifa.monto.toLocaleString()}</p>
                <p className="detalles">
                  Fecha de Nacimiento: {new Date(tarifa.fechaNacimiento).toLocaleDateString()}
                </p>
              </div>
            </div>
          ))}
        </div>

        {tarifas.length > 0 && (
          <div className="total-section">
            <h2>Total a Pagar: $ {tarifas.reduce((sum, t) => sum + t.monto, 0).toLocaleString()}</h2>
            <button 
              onClick={irAPagar} 
              className="btn-pagar"
              disabled={loading}
            >
              {loading ? 'Procesando...' : 'Continuar al Pago'}
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

export default Tarifas;