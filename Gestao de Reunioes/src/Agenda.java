import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Agenda {
    private List<Evento> eventosAgendados;
    private List<Evento> eventosConvidados;
    private List<LocalDate> datasOcupadas;

    public Agenda() {
        this.eventosAgendados = new ArrayList<>();
        this.eventosConvidados = new ArrayList<>();
        datasOcupadas = new ArrayList<>();
    }

    public List<Evento> getEventosAgendados() {
        return eventosAgendados;
    }

    public void removerEvento(Evento evento) {
        eventosAgendados.remove(evento);
    }

    public void adicionarEventoCriado(Evento evento) {
        eventosAgendados.add(evento);
    }

    public List<Evento> consultarAgendaDiaria(LocalDate data) {
        List<Evento> eventosDiarios = new ArrayList<>();
        for (Evento evento : eventosAgendados) {
            if (evento.getData().isEqual(data)) {
                eventosDiarios.add(evento);
            }
        }

        for (Evento evento : eventosConvidados) {
            if (evento.getData().isEqual(data)) {
                eventosDiarios.add(evento);
            }
        }
        return eventosDiarios;
    }

    public List<Evento> consultarAgendaSemanal(LocalDate dataInicial, LocalDate dataFinal) {
        List<Evento> eventosSemanais = new ArrayList<>();

        for (Evento evento : eventosAgendados) {
            if (!evento.getData().isBefore(dataInicial) && !evento.getData().isAfter(dataFinal.plusDays(1))) {
                eventosSemanais.add(evento);
            }
        }

        for (Evento evento : eventosConvidados) {
            if (!evento.getData().isBefore(dataInicial) && !evento.getData().isAfter(dataFinal.plusDays(1))) {
                eventosSemanais.add(evento);
            }
        }

        return eventosSemanais;
    }


    public List<Evento> consultarAgendaMensal(int mes, int ano) {
        List<Evento> eventosMensais = new ArrayList<>();
        for (Evento evento : eventosAgendados) {
            if (evento.getData().getMonthValue() == mes && evento.getData().getYear() == ano) {
                eventosMensais.add(evento);
            }
        }

        for (Evento evento : eventosConvidados) {
            if (evento.getData().getMonthValue() == mes && evento.getData().getYear() == ano) {
                eventosMensais.add(evento);
            }
        }
        return eventosMensais;
    }
}
