import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import Header from './components/Header';
import CrearCampanaForm from './components/CrearCampanaForm';
import ResultsView from './components/ResultsView';
import CampanaStateManager from './components/CampanaStateManager';
import CampanaEditor from './components/CampanaEditor';
import CommandHistory from './components/CommandHistory';
import './App.css';

const theme = createTheme({
  palette: {
    primary: {
      main: '#1976d2',
    },
    secondary: {
      main: '#dc004e',
    },
  },
});

function App() {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <Router>
        <div className="App">
          <Header />
          <Routes>
            <Route path="/" element={<CrearCampanaForm />} />
            <Route path="/campanas" element={<ResultsView />} />
            <Route path="/estados" element={<CampanaStateManager />} />
            <Route path="/editor" element={<CampanaEditor />} />
            <Route path="/historial" element={<CommandHistory />} />
          </Routes>
        </div>
      </Router>
    </ThemeProvider>
  );
}

export default App;