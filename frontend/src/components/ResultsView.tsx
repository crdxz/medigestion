import React, { useEffect, useState } from 'react';
import { 
  Container, 
  Paper, 
  Table, 
  TableBody, 
  TableCell, 
  TableContainer, 
  TableHead, 
  TableRow,
  Typography,
  Box,
  Alert,
  Chip,
  Tooltip
} from '@mui/material';
import axios from 'axios';

interface DoctorDTO {
  id: number;
  nombre: string;
  apellido: string;
  especialidad: string;
  licencia: string;
}

interface EmpresaDTO {
  id: number;
  nombre: string;
  nit: string;
  email: string;
}

interface EmpleadoDTO {
  id: number;
  nombre: string;
  apellido: string;
  cargo: string;
}

interface Campana {
  id: number;
  nombre: string;
  descripcion: string;
  fechaInicio: string;
  fechaFin: string;
  estado: string;
  tipo: string;
  tipoPromocion: string;
  grupoObjetivo: string;
  tema: string;
  tipoExamen: string;
  doctor: DoctorDTO;
  empresa: EmpresaDTO;
  coordinador: EmpleadoDTO;
}

const ResultsView: React.FC = () => {
  const [campanas, setCampanas] = useState<Campana[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchCampanas = async () => {
      try {
        console.log('Intentando conectar con el backend...');
        const response = await axios.get('http://localhost:8081/api/campanas', {
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
          },
          withCredentials: true,
          timeout: 5000 // 5 segundos de timeout
        });
        console.log('Respuesta recibida:', response.data);
        setCampanas(response.data);
        setLoading(false);
      } catch (err) {
        console.error('Error detallado:', err);
        if (axios.isAxiosError(err)) {
          if (err.code === 'ECONNABORTED') {
            setError('La conexión al servidor ha excedido el tiempo de espera');
          } else if (err.code === 'ERR_NETWORK') {
            setError('No se pudo conectar con el servidor. Por favor, verifica que el servidor esté corriendo en http://localhost:8081');
          } else {
            const errorMessage = err.response 
              ? `Error ${err.response.status}: ${err.response.statusText}`
              : err.message;
            const errorDetails = err.response?.data 
              ? `\nDetalles: ${JSON.stringify(err.response.data)}`
              : '';
            setError(`Error al cargar las campañas: ${errorMessage}${errorDetails}`);
          }
          console.error('URL del error:', err.config?.url);
          console.error('Método:', err.config?.method);
          console.error('Headers:', err.config?.headers);
          console.error('Response:', err.response?.data);
        } else {
          setError('Error al cargar las campañas');
        }
        setLoading(false);
      }
    };

    fetchCampanas();
  }, []);

  const getEstadoColor = (estado: string) => {
    switch (estado) {
      case 'PENDIENTE':
        return 'warning';
      case 'ACTIVA':
        return 'success';
      case 'FINALIZADA':
        return 'info';
      case 'CANCELADA':
        return 'error';
      default:
        return 'default';
    }
  };

  const formatDate = (dateString: string) => {
    try {
      const date = new Date(dateString);
      return date.toLocaleDateString('es-ES', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit'
      });
    } catch (error) {
      return dateString;
    }
  };

  if (loading) {
    return (
      <Container>
        <Typography>Cargando...</Typography>
      </Container>
    );
  }

  if (error) {
    return (
      <Container>
        <Alert severity="error" sx={{ mt: 2 }}>
          {error}
        </Alert>
        <Typography variant="body2" color="text.secondary" sx={{ mt: 2 }}>
          Por favor, asegúrate de que el servidor backend esté corriendo en http://localhost:8081
        </Typography>
      </Container>
    );
  }

  return (
    <Container maxWidth="lg" sx={{ mt: 4 }}>
      <Box sx={{ mb: 4 }}>
        <Typography variant="h4" component="h1" gutterBottom>
          Campañas
        </Typography>
      </Box>
      
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>ID</TableCell>
              <TableCell>Nombre</TableCell>
              <TableCell>Descripción</TableCell>
              <TableCell>Fechas</TableCell>
              <TableCell>Estado</TableCell>
              <TableCell>Tipo</TableCell>
              <TableCell>Doctor</TableCell>
              <TableCell>Empresa</TableCell>
              <TableCell>Coordinador</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {campanas.map((campana) => (
              <TableRow key={campana.id}>
                <TableCell>{campana.id}</TableCell>
                <TableCell>{campana.nombre}</TableCell>
                <TableCell>
                  <Tooltip title={campana.descripcion}>
                    <Typography noWrap sx={{ maxWidth: 200 }}>
                      {campana.descripcion}
                    </Typography>
                  </Tooltip>
                </TableCell>
                <TableCell>
                  <Typography variant="body2">
                    Inicio: {formatDate(campana.fechaInicio)}
                  </Typography>
                  <Typography variant="body2">
                    Fin: {formatDate(campana.fechaFin)}
                  </Typography>
                </TableCell>
                <TableCell>
                  <Chip 
                    label={campana.estado} 
                    color={getEstadoColor(campana.estado) as any}
                    size="small"
                  />
                </TableCell>
                <TableCell>
                  <Typography variant="body2">{campana.tipo}</Typography>
                  {campana.tipoExamen && (
                    <Typography variant="caption" display="block">
                      Examen: {campana.tipoExamen}
                    </Typography>
                  )}
                </TableCell>
                <TableCell>
                  {campana.doctor && (
                    <Typography variant="body2">
                      {campana.doctor.nombre} {campana.doctor.apellido}
                    </Typography>
                  )}
                </TableCell>
                <TableCell>
                  {campana.empresa && (
                    <Typography variant="body2">
                      {campana.empresa.nombre}
                    </Typography>
                  )}
                </TableCell>
                <TableCell>
                  {campana.coordinador && (
                    <Typography variant="body2">
                      {campana.coordinador.nombre} {campana.coordinador.apellido}
                    </Typography>
                  )}
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Container>
  );
};

export default ResultsView; 