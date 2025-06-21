import React, { useState, useEffect } from 'react';
import {
  Box,
  Paper,
  Typography,
  List,
  ListItem,
  ListItemText,
  ListItemIcon,
  Chip,
  Button,
  Alert,
  CircularProgress,
  Divider,
  Accordion,
  AccordionSummary,
  AccordionDetails,
  IconButton,
  Tooltip
} from '@mui/material';
import {
  History as HistoryIcon,
  Add as AddIcon,
  Edit as EditIcon,
  Delete as DeleteIcon,
  Undo as UndoIcon,
  ExpandMore as ExpandMoreIcon,
  Refresh as RefreshIcon,
  Info as InfoIcon,
  CheckCircle as SuccessIcon,
  Error as ErrorIcon,
  Warning as WarningIcon
} from '@mui/icons-material';
import axios from 'axios';

interface CommandHistoryItem {
  description: string;
  usuario: string;
  timestamp: string;
  success: boolean;
  errorMessage?: string;
}

interface CommandStats {
  totalCommands: number;
  successfulCommands: number;
  failedCommands: number;
  lastCommandTime: string;
}

const CommandHistory: React.FC = () => {
  const [history, setHistory] = useState<CommandHistoryItem[]>([]);
  const [stats, setStats] = useState<CommandStats | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [undoLoading, setUndoLoading] = useState(false);

  useEffect(() => {
    fetchCommandHistory();
  }, []);

  const fetchCommandHistory = async () => {
    try {
      setLoading(true);
      // Simular historial de comandos (en un caso real, esto vendría del backend)
      const mockHistory: CommandHistoryItem[] = [
        {
          description: "Crear campaña: Campaña de Vacunación COVID-19",
          usuario: "admin",
          timestamp: new Date(Date.now() - 1000 * 60 * 30).toISOString(), // 30 min ago
          success: true
        },
        {
          description: "Modificar campaña ID: 1 - Actualizar fechas",
          usuario: "admin",
          timestamp: new Date(Date.now() - 1000 * 60 * 15).toISOString(), // 15 min ago
          success: true
        },
        {
          description: "Cambiar estado campaña ID: 1 a ACTIVA",
          usuario: "admin",
          timestamp: new Date(Date.now() - 1000 * 60 * 5).toISOString(), // 5 min ago
          success: true
        },
        {
          description: "Eliminar campaña ID: 2",
          usuario: "admin",
          timestamp: new Date(Date.now() - 1000 * 60 * 2).toISOString(), // 2 min ago
          success: false,
          errorMessage: "No se puede eliminar una campaña activa"
        }
      ];

      setHistory(mockHistory);
      
      // Calcular estadísticas
      const totalCommands = mockHistory.length;
      const successfulCommands = mockHistory.filter(cmd => cmd.success).length;
      const failedCommands = totalCommands - successfulCommands;
      const lastCommandTime = mockHistory[0]?.timestamp || '';

      setStats({
        totalCommands,
        successfulCommands,
        failedCommands,
        lastCommandTime
      });

      setLoading(false);
    } catch (err: any) {
      setError('Error al cargar el historial de comandos');
      setLoading(false);
    }
  };

  const handleUndo = async () => {
    setUndoLoading(true);
    try {
      await axios.post('http://localhost:8081/api/campanas/undo');
      
      // Simular actualización del historial
      const newHistory = [...history];
      newHistory.unshift({
        description: "Deshacer última operación",
        usuario: "admin",
        timestamp: new Date().toISOString(),
        success: true
      });
      
      setHistory(newHistory);
      setError(null);
    } catch (err: any) {
      setError('Error al deshacer la última operación');
    } finally {
      setUndoLoading(false);
    }
  };

  const getCommandIcon = (description: string) => {
    if (description.includes('Crear')) return <AddIcon />;
    if (description.includes('Modificar')) return <EditIcon />;
    if (description.includes('Eliminar')) return <DeleteIcon />;
    if (description.includes('Deshacer')) return <UndoIcon />;
    return <HistoryIcon />;
  };

  const getCommandColor = (success: boolean) => {
    return success ? 'success' : 'error';
  };

  const formatTimestamp = (timestamp: string) => {
    const date = new Date(timestamp);
    const now = new Date();
    const diffInMinutes = Math.floor((now.getTime() - date.getTime()) / (1000 * 60));
    
    if (diffInMinutes < 1) return 'Hace un momento';
    if (diffInMinutes < 60) return `Hace ${diffInMinutes} minutos`;
    if (diffInMinutes < 1440) return `Hace ${Math.floor(diffInMinutes / 60)} horas`;
    return date.toLocaleDateString();
  };

  if (loading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" minHeight="400px">
        <CircularProgress />
      </Box>
    );
  }

  return (
    <Box sx={{ p: 3 }}>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
        <Typography variant="h4" component="h1">
          Historial de Comandos
        </Typography>
        <Box display="flex" gap={2}>
          <Button
            variant="outlined"
            startIcon={<RefreshIcon />}
            onClick={fetchCommandHistory}
          >
            Actualizar
          </Button>
          <Button
            variant="contained"
            startIcon={<UndoIcon />}
            onClick={handleUndo}
            disabled={undoLoading}
          >
            Deshacer Última Operación
          </Button>
        </Box>
      </Box>

      {error && (
        <Alert severity="error" sx={{ mb: 2 }}>
          {error}
        </Alert>
      )}

      {/* Estadísticas */}
      {stats && (
        <Paper sx={{ p: 2, mb: 3 }}>
          <Typography variant="h6" gutterBottom>
            Estadísticas de Comandos
          </Typography>
          <Box display="flex" gap={2} flexWrap="wrap">
            <Chip
              icon={<InfoIcon />}
              label={`Total: ${stats.totalCommands}`}
              color="primary"
            />
            <Chip
              icon={<SuccessIcon />}
              label={`Exitosos: ${stats.successfulCommands}`}
              color="success"
            />
            <Chip
              icon={<ErrorIcon />}
              label={`Fallidos: ${stats.failedCommands}`}
              color="error"
            />
            <Chip
              icon={<HistoryIcon />}
              label={`Último: ${formatTimestamp(stats.lastCommandTime)}`}
              color="info"
            />
          </Box>
        </Paper>
      )}

      {/* Lista de comandos */}
      <Paper>
        <List>
          {history.map((command, index) => (
            <React.Fragment key={index}>
              <ListItem>
                <ListItemIcon>
                  <Tooltip title={command.success ? 'Comando exitoso' : 'Comando fallido'}>
                    <Box>
                      {command.success ? (
                        <SuccessIcon color="success" />
                      ) : (
                        <ErrorIcon color="error" />
                      )}
                    </Box>
                  </Tooltip>
                </ListItemIcon>
                <ListItemText
                  primary={
                    <Box display="flex" alignItems="center" gap={1}>
                      {getCommandIcon(command.description)}
                      <Typography variant="body1" component="span">
                        {command.description}
                      </Typography>
                      <Chip
                        label={command.success ? 'Exitoso' : 'Fallido'}
                        color={getCommandColor(command.success) as any}
                        size="small"
                      />
                    </Box>
                  }
                  secondary={
                    <Box component="span">
                      <Typography variant="body2" color="text.secondary" component="span">
                        Usuario: {command.usuario} • {formatTimestamp(command.timestamp)}
                      </Typography>
                      {command.errorMessage && (
                        <Alert severity="error" sx={{ mt: 1 }}>
                          {command.errorMessage}
                        </Alert>
                      )}
                    </Box>
                  }
                />
              </ListItem>
              {index < history.length - 1 && <Divider />}
            </React.Fragment>
          ))}
        </List>
      </Paper>

      {/* Información adicional */}
      <Accordion sx={{ mt: 3 }}>
        <AccordionSummary expandIcon={<ExpandMoreIcon />}>
          <Typography variant="h6">
            Información sobre el Patrón Comando
          </Typography>
        </AccordionSummary>
        <AccordionDetails>
          <Typography paragraph>
            El <strong>Patrón Comando</strong> implementado en este sistema permite:
          </Typography>
          <ul>
            <li>
              <Typography variant="body2">
                <strong>Auditoría completa:</strong> Todas las operaciones quedan registradas con usuario y timestamp
              </Typography>
            </li>
            <li>
              <Typography variant="body2">
                <strong>Deshacer operaciones:</strong> Capacidad de revertir la última operación realizada
              </Typography>
            </li>
            <li>
              <Typography variant="body2">
                <strong>Trazabilidad:</strong> Historial completo de comandos ejecutados
              </Typography>
            </li>
            <li>
              <Typography variant="body2">
                <strong>Validación:</strong> Cada comando valida si puede ser ejecutado antes de proceder
              </Typography>
            </li>
          </ul>
        </AccordionDetails>
      </Accordion>
    </Box>
  );
};

export default CommandHistory; 