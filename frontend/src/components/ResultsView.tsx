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
  Tooltip,
  IconButton,
  Collapse,
  CircularProgress
} from '@mui/material';
import Add from '@mui/icons-material/Add';
import Remove from '@mui/icons-material/Remove';
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
  empleados?: EmpleadoDTO[];
}

interface EmpleadoDTO {
  id: number;
  nombre: string;
  apellido: string;
  cargo: string;
  email?: string;
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
  const [expanded, setExpanded] = useState<{ [key: number]: boolean }>({});

  useEffect(() => {
    const fetchCampanas = async () => {
      try {
        const response = await axios.get('http://localhost:8081/api/campanas', {
          timeout: 10000
        });
        setCampanas(response.data);
        setLoading(false);
      } catch (err) {
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

  const handleExpandClick = (campanaId: number) => {
    setExpanded(prev => ({
      ...prev,
      [campanaId]: !prev[campanaId]
    }));
  };

  if (loading) {
    return (
      <Container
        maxWidth="lg"
        sx={{
          minHeight: '100vh',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center'
        }}
      >
        <CircularProgress />
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
              <TableCell />
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
              <React.Fragment key={campana.id}>
                <TableRow>
                  <TableCell>
                    <IconButton
                      size="small"
                      onClick={() => handleExpandClick(campana.id)}
                      aria-label={expanded[campana.id] ? 'Ocultar empleados' : 'Mostrar empleados'}
                    >
                      {expanded[campana.id] ? <Remove /> : <Add />}
                    </IconButton>
                  </TableCell>
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
                <TableRow>
                  <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={10}>
                    <Collapse in={expanded[campana.id]} timeout="auto" unmountOnExit>
                      <Box margin={2}>
                        <Typography variant="subtitle1" gutterBottom>
                          Empleados de la campaña
                        </Typography>
                        {campana.empresa && campana.empresa.empleados && campana.empresa.empleados.length > 0 ? (
                          <Table size="small">
                            <TableHead>
                              <TableRow>
                                <TableCell>ID</TableCell>
                                <TableCell>Nombre</TableCell>
                                <TableCell>Apellido</TableCell>
                                <TableCell>Cargo</TableCell>
                                <TableCell>Email</TableCell>
                              </TableRow>
                            </TableHead>
                            <TableBody>
                              {campana.empresa.empleados.map((empleado) => (
                                <TableRow key={empleado.id}>
                                  <TableCell>{empleado.id}</TableCell>
                                  <TableCell>{empleado.nombre}</TableCell>
                                  <TableCell>{empleado.apellido}</TableCell>
                                  <TableCell>{empleado.cargo}</TableCell>
                                  <TableCell>{empleado.email || '-'}</TableCell>
                                </TableRow>
                              ))}
                            </TableBody>
                          </Table>
                        ) : (
                          <Typography variant="body2" color="text.secondary">
                            No hay empleados asociados a esta campaña.
                          </Typography>
                        )}
                      </Box>
                    </Collapse>
                  </TableCell>
                </TableRow>
              </React.Fragment>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Container>
  );
};

export default ResultsView;