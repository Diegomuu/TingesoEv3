import React, { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import "../assets/styles.css";
import "./Pagos.css";
import Navbar from "./Navbar";

const Pagos = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  
  const { tarifas, montoTotal, vueltas, cantidadPersonas, reserva } = location.state || {
    tarifas: [],
    montoTotal: 0,
    vueltas: 0,
    cantidadPersonas: 0,
    reserva: null
  };

  // Añadir mensaje de depuración
  console.log('Número de pilotos:', tarifas.length);
  console.log('Datos de pilotos:', tarifas);

  const handleDescargarExcel = async () => {
    if (!reserva?.codigo) {
      setError("No se encontró el código de reserva");
      return;
    }

    setLoading(true);
    setError(null);

    try {
      console.log('Intentando descargar comprobante con código:', reserva.codigo);
      const response = await fetch(`${import.meta.env.VITE_BASE}/reservas/comprobante/${reserva.codigo}`);
      
      console.log('Respuesta del servidor:', {
        status: response.status,
        ok: response.ok,
        statusText: response.statusText
      });

      if (!response.ok) {
        if (response.status === 404) {
          throw new Error('No se encontró el archivo del comprobante. Por favor, asegúrate de que el comprobante haya sido generado correctamente.');
        }
        throw new Error(`Error al descargar el comprobante: ${response.statusText}`);
      }

      const blob = await response.blob();
      console.log('Blob recibido:', {
        size: blob.size,
        type: blob.type
      });

      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = `comprobante_reserva_${reserva.codigo}.xlsx`;
      document.body.appendChild(a);
      a.click();
      window.URL.revokeObjectURL(url);
      document.body.removeChild(a);

      console.log('Descarga iniciada correctamente');
    } catch (error) {
      console.error('Error detallado:', error);
      setError(error.message || 'Error desconocido al descargar el comprobante');
    } finally {
      setLoading(false);
    }
  };

  const handleFinalizarReserva = async () => {
    setLoading(true);
    setError(null);

    try {
      // Incrementar visitas mensuales para todos los clientes
      const clienteIds = reserva.clienteIds;
      const response = await fetch(`${import.meta.env.VITE_BASE}/clientes/incrementar-visitas`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(clienteIds)
      });

      if (!response.ok) {
        throw new Error('Error al actualizar las visitas mensuales');
      }

      // Redirigir al home
      navigate('/');
    } catch (error) {
      console.error('Error:', error);
      setError('Error al finalizar la reserva: ' + error.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="pagos-container">
      <Navbar />
      <div className="pagos-content">
        <h1>Resumen de Reserva</h1>
        
        {/* Datos de la Reserva */}
        <div className="resumen-section">
          <h2>Detalles de la Reserva</h2>
          <div className="info-card">
            <p><strong>Código de Reserva:</strong> {reserva?.codigo}</p>
            <p><strong>Fecha de Reserva:</strong> {reserva?.fechaReserva}</p>
            <p><strong>Hora de Reserva:</strong> {reserva?.horaReserva}</p>
            <p><strong>Vueltas:</strong> {vueltas}</p>
            <p><strong>Cantidad de Personas:</strong> {cantidadPersonas}</p>
            <p><strong>Reservante:</strong> {reserva?.nombreReservante}</p>
          </div>
        </div>

        {/* Lista de Clientes y sus Tarifas */}
        <div className="resumen-section">
          <h2>Detalle de Tarifas por Cliente</h2>
          <div className="tarifas-grid">
            {tarifas.map((tarifa, index) => (
              <div key={index} className="tarifa-card">
                <h3>{tarifa.nombre}</h3>
                <p><strong>Monto:</strong> ${tarifa.monto?.toLocaleString()}</p>
                <p><strong>Fecha de Nacimiento:</strong> {new Date(tarifa.fechaNacimiento).toLocaleDateString()}</p>
              </div>
            ))}
          </div>
        </div>

        {/* Resumen de Montos */}
        <div className="resumen-section">
          <h2>Resumen de Pago</h2>
          <div className="info-card total-card">
            <p><strong>Subtotal:</strong> ${montoTotal?.toLocaleString()}</p>
            <p><strong>IVA (19%):</strong> ${(montoTotal * 0.19)?.toLocaleString()}</p>
            <p className="total"><strong>Total a Pagar:</strong> ${(montoTotal * 1.19)?.toLocaleString()}</p>
          </div>
        </div>

        {/* Botones de Acción */}
        <div className="actions-section">
          <button 
            onClick={handleDescargarExcel} 
            className="btn-action"
            disabled={loading || !reserva?.codigo}
          >
            {loading ? 'Descargando...' : 'Descargar Comprobante Excel'}
          </button>
          <button 
            onClick={handleFinalizarReserva}
            className="btn-action btn-finalizar"
            disabled={loading}
          >
            {loading ? 'Procesando...' : 'Finalizar y Volver al Inicio'}
          </button>
        </div>

        {/* Mensaje de Error */}
        {error && (
          <div className="error-message">
            {error}
          </div>
        )}
      </div>
    </div>
  );
};

export default Pagos;
