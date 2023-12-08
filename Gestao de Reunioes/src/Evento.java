import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public abstract class Evento {
    private String descricao;
    private String data;
    private Usuario criador;
    private List<Usuario> convidados;
    private List<LocalDate> datasSugeridas;

    public Evento(String descricao, String data) {
        datasSugeridas = new ArrayList<>();
        convidados = new ArrayList<>();
        this.descricao = descricao;
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }


    public LocalDate getData() {
        return LocalDate.parse(this.data, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public void setCriador(Usuario criador) {
        this.criador = criador;
    }

    public abstract double calcularCusto();


    public List<Usuario> getConvidados() {
        return convidados;
    }

    public void convidarUsuario(Usuario usuario) {
        convidados.add(usuario);
        Notificacao notificacao = new Notificacao(usuario, "Voce foi convidado para o evento: " + descricao);
        notificacao.setRemetente(criador); // Define o remetente da notificação como o criador do evento
        notificacao.notificarEvento(this); // Notifica o evento ao usuário convidado
    }

}