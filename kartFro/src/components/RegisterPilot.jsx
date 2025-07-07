import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import "../assets/styles.css";
import "./RegisterPilot.css";
import Navbar from './Navbar';

const RegisterPilot = () => {
  const [numClients, setNumClients] = useState(1);
  const [clients, setClients] = useState([{ name: '', birthday: '' }]);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  // Agregar estado para el estado de búsqueda de cada cliente
  const [clientSearchStatus, setClientSearchStatus] = useState({});

  const handleNumClientsChange = (e) => {
    const value = parseInt(e.target.value, 10);
    setNumClients(value);

    const updatedClients = [...clients];
    while (updatedClients.length < value) {
      updatedClients.push({ name: '', birthday: '' });
    }
    while (updatedClients.length > value) {
      updatedClients.pop();
    }
    setClients(updatedClients);
  };

  const handleClientChange = (index, field, value) => {
    const updatedClients = [...clients];
    updatedClients[index][field] = value;
    setClients(updatedClients);
  };

  // Función de búsqueda en tiempo real
  const handleNameChange = async (index, value) => {
    handleClientChange(index, 'name', value);
    
    if (value.length > 2) { // Buscar solo si tiene más de 2 caracteres
      setClientSearchStatus(prev => ({ ...prev, [index]: 'searching' }));
      
      try {
        const clienteExistente = await buscarCliente(value);
        setClientSearchStatus(prev => ({ 
          ...prev, 
          [index]: clienteExistente ? 'found' : 'not-found' 
        }));
      } catch (error) {
        setClientSearchStatus(prev => ({ ...prev, [index]: 'error' }));
      }
    } else {
      setClientSearchStatus(prev => ({ ...prev, [index]: null }));
    }
  };

  const buscarCliente = async (nombre) => {
    try {
      console.log('Buscando cliente:', nombre);
      const response = await fetch(`${import.meta.env.VITE_BASE}/clientes/buscar/${nombre}`);
      
      console.log('Respuesta de búsqueda:', {
        status: response.status,
        ok: response.ok
      });

      if (response.status === 404) {
        return null;
      }
      
      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(`Error en la búsqueda: ${response.status}. ${errorText}`);
      }

      const data = await response.json();
      console.log('Cliente encontrado:', data);
      return data;
    } catch (error) {
      console.error('Error al buscar cliente:', error);
      throw error;
    }
  };

  const registrarCliente = async (clienteData) => {
    try {
      console.log('Intentando registrar cliente con datos:', clienteData);
      
      const response = await fetch(`${import.meta.env.VITE_BASE}/clientes/crear`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(clienteData),
      });

      console.log('Respuesta del registro:', {
        status: response.status,
        ok: response.ok
      });

      if (!response.ok) {
        throw new Error(`Error al registrar cliente: ${response.status}`);
      }

      // Después de registrar, buscar el cliente para obtener su ID
      const clienteRegistrado = await buscarCliente(clienteData.nombre);
      if (!clienteRegistrado) {
        throw new Error('No se pudo obtener el ID del cliente registrado');
      }

      return clienteRegistrado;
    } catch (error) {
      console.error('Error en registrarCliente:', error);
      throw error;
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      const clientesProcessados = [];

      for (const client of clients) {
        try {
          console.log('Procesando cliente:', client);
          let clienteInfo = {
            nombre: client.name,
            cumpleanos: client.birthday,
            visitasMensuales: 0
          };

          // Buscar si el cliente existe
          let clienteExistente = await buscarCliente(client.name);

          if (clienteExistente) {
            // Si existe, usar sus datos existentes
            clienteInfo = {
              ...clienteExistente,
              visitasMensuales: clienteExistente.visitasMensuales || 0
            };
            console.log(`Cliente ${client.name} encontrado con ID ${clienteInfo.id}`);
          } else {
            // Si no existe, registrarlo
            clienteInfo = await registrarCliente(clienteInfo);
            console.log(`Nuevo cliente ${client.name} registrado con ID ${clienteInfo.id}`);
          }

          clientesProcessados.push(clienteInfo);
        } catch (error) {
          console.error(`Error procesando cliente ${client.name}:`, error);
          throw new Error(`Error con cliente ${client.name}: ${error.message}`);
        }
      }

      // Guardar en localStorage
      localStorage.setItem('cantidadClientes', numClients.toString());
      localStorage.setItem('clientesData', JSON.stringify(clientesProcessados));

      alert("¡Proceso completado con éxito!");
      
      // Navegar a Tarifas con los datos necesarios
      navigate('/tarifas', { 
        state: { 
          clientes: clientesProcessados,
          cantidadPersonas: numClients
        }
      });
    } catch (error) {
      console.error("Error detallado en el proceso:", error);
      alert(`Error: ${error.message}`);
    } finally {
      setLoading(false);
    }
  };

  const [summary, setSummary] = useState(null);

  const generateSummary = async () => {
    const summaryData = [];
    
    for (let i = 0; i < clients.length; i++) {
      const client = clients[i];
      if (client.name) {
        const clienteExistente = await buscarCliente(client.name);
        summaryData.push({
          name: client.name,
          isExisting: !!clienteExistente,
          visits: clienteExistente?.visitasMensuales || 0
        });
      }
    }
    
    setSummary(summaryData);
    return summaryData; // Retornar los datos del resumen para usar en el modal
  };

  const [notifications, setNotifications] = useState([]);

  const addNotification = (message, type = 'info') => {
    const id = Date.now();
    setNotifications(prev => [...prev, { id, message, type }]);
    setTimeout(() => {
      setNotifications(prev => prev.filter(n => n.id !== id));
    }, 3000);
  };

  const [showConfirmation, setShowConfirmation] = useState(false);
  const [confirmationData, setConfirmationData] = useState(null);

  const proceedWithRegistration = async () => {
    setShowConfirmation(false);
    setLoading(true); // Activar el estado de carga para el envío final

    try {
      const summaryData = await generateSummary();
      setConfirmationData(summaryData);
      setShowConfirmation(true);

      const clientesProcessados = [];

      for (const client of clients) {
        try {
          console.log('Procesando cliente:', client);
          let clienteInfo = {
            nombre: client.name,
            cumpleanos: client.birthday,
            visitasMensuales: 0
          };

          // Buscar si el cliente existe
          let clienteExistente = await buscarCliente(client.name);

          if (clienteExistente) {
            // Si existe, usar sus datos existentes
            clienteInfo = {
              ...clienteExistente,
              visitasMensuales: clienteExistente.visitasMensuales || 0
            };
            addNotification(`✅ ${client.name} encontrado en el sistema`, 'success');
            console.log(`Cliente ${client.name} encontrado con ID ${clienteInfo.id}`);
          } else {
            // Si no existe, registrarlo
            clienteInfo = await registrarCliente(clienteInfo);
            addNotification(`🆕 ${client.name} registrado como nuevo cliente`, 'info');
            console.log(`Nuevo cliente ${client.name} registrado con ID ${clienteInfo.id}`);
          }

          clientesProcessados.push(clienteInfo);
        } catch (error) {
          console.error(`Error procesando cliente ${client.name}:`, error);
          addNotification(`Error con cliente ${client.name}: ${error.message}`, 'error');
          throw error;
        }
      }

      // Guardar en localStorage
      localStorage.setItem('cantidadClientes', numClients.toString());
      localStorage.setItem('clientesData', JSON.stringify(clientesProcessados));

      alert("¡Proceso completado con éxito!");
      
      // Navegar a Tarifas con los datos necesarios
      navigate('/tarifas', { 
        state: { 
          clientes: clientesProcessados,
          cantidadPersonas: numClients
        }
      });
    } catch (error) {
      console.error("Error detallado en el proceso:", error);
      alert(`Error: ${error.message}`);
    } finally {
      setLoading(false);
    }
  };

  const [progress, setProgress] = useState({ current: 0, total: 0, details: [] });

  const processClientsWithProgress = async () => {
    setProgress({ current: 0, total: clients.length, details: [] });
    
    for (let i = 0; i < clients.length; i++) {
      const client = clients[i];
      setProgress(prev => ({ 
        ...prev, 
        current: i + 1,
        details: [...prev.details, `Procesando ${client.name}...`]
      }));
      
      // Procesar cliente...
      
      setProgress(prev => ({ 
        ...prev, 
        details: [...prev.details.slice(0, -1), `✅ ${client.name} completado`]
      }));
    }
  };

  return (
    <div>
      <Navbar />
      <div className="register-pilot-container">
        <div className="register-pilot-header">
          <h1>🏎️ Registrar Cliente</h1>
          <p>Registra nuevos clientes para el servicio de karting</p>
        </div>

        <form onSubmit={handleSubmit}>
          <div className="number-selector">
            <label htmlFor="numClients">¿Cuántos clientes deseas registrar? (máximo 15)</label>
            <select 
              id="numClients" 
              value={numClients} 
              onChange={handleNumClientsChange}
              disabled={loading}
            >
              {Array.from({ length: 15 }, (_, i) => i + 1).map(num => (
                <option key={num} value={num}>
                  {num} {num === 1 ? 'cliente' : 'clientes'}
                </option>
              ))}
            </select>
          </div>

          <div className="clients-grid">
            {clients.map((client, index) => (
              <div key={index} className="client-card">
                <h3>Cliente {index + 1}</h3>
                <div className="input-group">
                  <label>Nombre del Cliente</label>
                  <div className="input-with-status">
                    <input
                      type="text"
                      value={client.name}
                      onChange={(e) => handleNameChange(index, e.target.value)}
                      placeholder="Ingresa el nombre completo"
                      required
                      disabled={loading}
                      className={clientSearchStatus[index] ? `status-${clientSearchStatus[index]}` : ''}
                    />
                    {clientSearchStatus[index] === 'searching' && (
                      <span className="search-indicator">🔍 Buscando...</span>
                    )}
                    {clientSearchStatus[index] === 'found' && (
                      <span className="found-indicator">✅ Cliente existente</span>
                    )}
                    {clientSearchStatus[index] === 'not-found' && (
                      <span className="not-found-indicator">🆕 Nuevo cliente</span>
                    )}
                  </div>
                </div>
                <div className="input-group">
                  <label>Fecha de Nacimiento</label>
                  <input
                    type="date"
                    value={client.birthday}
                    onChange={(e) => handleClientChange(index, 'birthday', e.target.value)}
                    required
                    disabled={loading}
                  />
                </div>
              </div>
            ))}
          </div>

          <button type="submit" className="submit-button" disabled={loading}>
            {loading ? 'Procesando...' : 'Registrar Clientes'}
          </button>
        </form>

        {summary && (
          <div className="summary-panel">
            <h3>Resumen de Clientes</h3>
            {summary.map((item, index) => (
              <div key={index} className="summary-item">
                <span>{item.name}</span>
                <span className={item.isExisting ? 'existing' : 'new'}>
                  {item.isExisting ? `✅ Existente (${item.visits} visitas)` : '🆕 Nuevo'}
                </span>
              </div>
            ))}
          </div>
        )}

        {notifications.map(notification => (
          <div key={notification.id} className={`notification ${notification.type}`}>
            {notification.message}
          </div>
        ))}

        {showConfirmation && (
          <div className="confirmation-modal">
            <h3>Confirmar Registro</h3>
            <div className="confirmation-content">
              {confirmationData.map((item, index) => (
                <div key={index} className="confirmation-item">
                  <strong>{item.name}</strong>
                  <span>{item.isExisting ? 'Cliente existente' : 'Nuevo cliente'}</span>
                </div>
              ))}
            </div>
            <div className="confirmation-actions">
              <button onClick={() => setShowConfirmation(false)}>Cancelar</button>
              <button onClick={proceedWithRegistration}>Confirmar</button>
            </div>
          </div>
        )}

        {progress.details.length > 0 && (
          <div className="progress-bar">
            <h3>Progreso del Registro</h3>
            <div className="progress-details">
              {progress.details.map((detail, index) => (
                <p key={index}>{detail}</p>
              ))}
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default RegisterPilot;