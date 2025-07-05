import React, { useState, useEffect } from "react";
import "./RackSemanal.css";
import Navbar from "./Navbar";

const RackSemanal = () => {
  const [selectedDate, setSelectedDate] = useState(new Date());
  const [reservas, setReservas] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  // Función para obtener el lunes de la semana actual
  const getLunesSemana = (date) => {
    const tempDate = new Date(date);
    const day = tempDate.getDay() || 7;
    if (day !== 1) {
      tempDate.setHours(-24 * (day - 1));
    }
    return tempDate;
  };

  // Función para formatear fecha como YYYY-MM-DD
  const formatDate = (date) => {
    return date.toISOString().split('T')[0];
  };

  // Obtener reservas de la semana
  const fetchReservas = async (fecha) => {
    setLoading(true);
    setError(null);
    try {
      const response = await fetch(
        `${import.meta.env.VITE_BASE}/rack/rack-semanal?fecha=${formatDate(fecha)}`
      );
      
      if (!response.ok) {
        throw new Error('Error al cargar las reservas');
      }
      
      const data = await response.json();
      setReservas(data);
    } catch (err) {
      console.error('Error:', err);
      setError('Error al cargar las reservas: ' + err.message);
    } finally {
      setLoading(false);
    }
  };

  // Efecto para cargar reservas cuando cambia la fecha
  useEffect(() => {
    fetchReservas(selectedDate);
  }, [selectedDate]);

  // Obtener el rango de fechas de la semana
  const lunesSemana = getLunesSemana(selectedDate);
  const domingoSemana = new Date(lunesSemana);
  domingoSemana.setDate(domingoSemana.getDate() + 6);

  // Función para cambiar de semana
  const cambiarSemana = (direccion) => {
    const nuevaFecha = new Date(selectedDate);
    nuevaFecha.setDate(nuevaFecha.getDate() + (direccion * 7));
    setSelectedDate(nuevaFecha);
  };

  // Horarios de atención (bloques de 1 hora)
  const horarios = [
    "10:00", "11:00", "12:00", "13:00", "14:00",
    "15:00", "16:00", "17:00", "18:00", "19:00"
  ];

  // Días de la semana con fechas
  const diasSemana = Array.from({ length: 7 }, (_, i) => {
    const fecha = new Date(lunesSemana);
    fecha.setDate(fecha.getDate() + i);
    return {
      nombre: ["Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"][i],
      fecha: fecha
    };
  });

  // Función para verificar si hay una reserva en un horario específico
  const getReserva = (dia, hora) => {
    // Convertir la hora a número para comparación
    const horaNum = parseInt(hora.split(':')[0]);
    
    // Verificar que la hora esté dentro del rango permitido (10:00-19:00)
    if (horaNum < 10 || horaNum >= 20) {
      return null;
    }

    return reservas.find(r => {
      // Convertir la hora de la reserva a número
      const horaReserva = parseInt(r.horaReserva.split(':')[0]);
      
      // Solo retornar la reserva si está dentro del horario de atención
      return formatDate(new Date(r.fecha)) === formatDate(dia.fecha) && 
             horaReserva === horaNum &&
             horaReserva >= 10 && 
             horaReserva < 20;
    });
  };

  return (
    <div className="rack-container">
      <Navbar />
      <div className="rack-content">
        <h1>Rack Semanal de Reservas</h1>
        
        {/* Selector de semana */}
        <div className="semana-selector">
          <button 
            onClick={() => cambiarSemana(-1)}
            className="btn-semana"
            disabled={loading}
          >
            ← Semana anterior
          </button>
          
          <div className="semana-info">
            <input 
              type="date" 
              value={formatDate(selectedDate)}
              onChange={(e) => setSelectedDate(new Date(e.target.value))}
              className="fecha-selector"
              disabled={loading}
            />
            <span className="rango-semana">
              {formatDate(lunesSemana)} - {formatDate(domingoSemana)}
            </span>
          </div>

          <button 
            onClick={() => cambiarSemana(1)}
            className="btn-semana"
            disabled={loading}
          >
            Semana siguiente →
          </button>
        </div>

        {error && (
          <div className="error-message">
            {error}
          </div>
        )}

        {loading ? (
          <div className="loading">Cargando reservas...</div>
        ) : (
          <>
            <div className="rack-description">
              <p>Horario de atención: 10:00 - 20:00 hrs</p>
              <div className="rack-legend">
                <span className="disponible">Disponible</span>
                <span className="reservado">Reservado</span>
              </div>
            </div>
            
            <div className="rack-table">
              <div className="rack-header">
                <div className="hora-header">Hora</div>
                {diasSemana.map(dia => (
                  <div key={dia.nombre} className="dia-header">
                    <div className="dia-nombre">{dia.nombre}</div>
                    <div className="dia-fecha">{formatDate(dia.fecha)}</div>
                  </div>
                ))}
              </div>
              
              <div className="rack-body">
                {horarios.map(hora => (
                  <div key={hora} className="rack-row">
                    <div className="hora-cell">{hora}</div>
                    {diasSemana.map(dia => {
                      const reserva = getReserva(dia, hora);
                      const horaNum = parseInt(hora.split(':')[0]);
                      const fueraHorario = horaNum < 10 || horaNum >= 20;

                      return (
                        <div 
                          key={`${dia.nombre}-${hora}`} 
                          className={`rack-cell ${fueraHorario ? 'fuera-horario' : reserva ? 'reservado' : 'disponible'}`}
                          title={fueraHorario ? 
                            'Fuera del horario de atención (10:00-19:00)' :
                            reserva ? 
                              `${reserva.nombreReservante} - ${reserva.cantidadPersonas} personas - ${reserva.vueltas} vueltas` : 
                              `Disponible: ${dia.nombre} ${formatDate(dia.fecha)} - ${hora}`
                          }
                        >
                          {!fueraHorario && reserva && (
                            <div className="reserva-info">
                              <p className="reserva-nombre">{reserva.nombreReservante}</p>
                              <p className="reserva-detalles">
                                {reserva.cantidadPersonas} personas
                                <br />
                                {reserva.vueltas} vueltas
                              </p>
                            </div>
                          )}
                        </div>
                      );
                    })}
                  </div>
                ))}
              </div>
            </div>
          </>
        )}
      </div>
    </div>
  );
};

export default RackSemanal;