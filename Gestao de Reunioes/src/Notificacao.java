public class Notificacao {
    private String mensagem;
    private Usuario destinatario;
    private Usuario remetente;

    public Notificacao(Usuario destinatario, String mensagem) {
        this.destinatario = destinatario;
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public Usuario getRemetente() {
        return remetente;
    }

    public void setRemetente(Usuario remetente) {
        this.remetente = remetente;
    }

    public void notificarEvento(Evento evento) {
        if (destinatario != null) {
            destinatario.receberNotificacao(new Notificacao(destinatario, "Voce foi convidado para um evento: " + evento.getDescricao()));
        }
    }
}
