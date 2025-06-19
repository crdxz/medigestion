import React, { useState } from 'react';
import { ThemeProvider, createTheme, CssBaseline } from '@mui/material';
import ResultsView from './components/ResultsView';
import Header from './components/Header';
import CrearCampanaForm from './components/CrearCampanaForm';

const theme = createTheme({
  palette: {
    mode: 'light',
    primary: {
      main: '#223354', // Azul oscuro sobrio
    },
    secondary: {
      main: '#6c757d', // Gris sobrio
    },
    background: {
      default: '#f4f6f8', // Gris claro
      paper: '#ffffff',
    },
    text: {
      primary: '#222b45',
      secondary: '#6c757d',
    },
  },
  shape: {
    borderRadius: 16,
  },
  typography: {
    fontFamily: 'Segoe UI, Roboto, Arial, sans-serif',
    h5: {
      fontWeight: 700,
      letterSpacing: 0.5,
    },
    h4: {
      fontWeight: 700,
      letterSpacing: 0.5,
    },
  },
  components: {
    MuiPaper: {
      styleOverrides: {
        root: {
          boxShadow: '0 4px 24px 0 rgba(34,51,84,0.08)',
        },
      },
    },
    MuiButton: {
      styleOverrides: {
        root: {
          borderRadius: 12,
          textTransform: 'none',
          fontWeight: 600,
        },
      },
    },
    MuiTextField: {
      styleOverrides: {
        root: {
          background: '#f8fafc',
          borderRadius: 8,
        },
      },
    },
  },
});

function App() {
  const [view, setView] = useState<'inicio' | 'crear'>('inicio');

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <Header onSelect={setView} selected={view} />
      {view === 'inicio' ? <ResultsView /> : <CrearCampanaForm />}
    </ThemeProvider>
  );
}

export default App;