import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './components/Home';
import RegisterPilot from './components/RegisterPilot';
import Tarifas from './components/Tarifas';
import Pagos from './components/pagos';
import RackSemanal from './components/RackSemanal';
import Reportes from './components/Reportes';

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/register-pilot" element={<RegisterPilot />} />
        <Route path="/tarifas" element={<Tarifas />} />
        <Route path="/pagos" element={<Pagos />} />
        <Route path="/rack-semanal" element={<RackSemanal />} />
        <Route path="/reportes" element={<Reportes />} />
      </Routes>
    </Router>
  );
};

export default App;
