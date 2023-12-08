import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Usuario {
    private String nome;
    private String email;
    private String senha;
    private Agenda agenda;
    private List<Evento> eventosCriados;
    private List<Evento> eventosConvidados;
    private List<Notificacao> notificacoesRecebidas;
    private static List<Usuario> listaUsuarios = new ArrayList<>();

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.agenda = new Agenda();
        this.eventosCriados = new ArrayList<>();
        this.eventosConvidados = new ArrayList<>();
        this.notificacoesRecebidas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }

    public Agenda getAgendaPessoal() {
        return agenda;
    }

    public void removerEventoDaAgenda(Evento evento) {
        this.agenda.removerEvento(evento);
        this.eventosCriados = this.agenda.getEventosAgendados(); // Atualiza os eventos agendados após remover o evento
    }

    public void atualizarEventosAgendados() {
        this.eventosCriados = this.agenda.getEventosAgendados();
    }

    public List<Evento> consultarEventosConvidados() {
        List<Evento> eventosAgendados = new ArrayList<>();
        eventosAgendados.addAll(eventosCriados);
        eventosAgendados.addAll(eventosConvidados);

        return eventosAgendados;
    }

    public void receberNotificacao(Notificacao notificacao) {
        // Utiliza um stream para verificar se a notificação já existe na lista com base no remetente e na mensagem.
        // Se uma notificação idêntica já estiver presente, ela não será adicionada novamente.
        boolean notificacaoJaExiste = notificacoesRecebidas.
                stream().anyMatch(n -> n.getRemetente() == notificacao.getRemetente() && n.getMensagem().equals(notificacao.getMensagem()));

        if (!notificacaoJaExiste) {
            notificacoesRecebidas.add(notificacao);
        }
    }


    public List<Notificacao> getNotificacoesRecebidas() {
        return notificacoesRecebidas;
    }

    public void limparNotificacoes() {
        notificacoesRecebidas.clear();
    }

    public static Usuario cadastrarUsuario(Scanner scanner) {
        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine();

        String email = "";
        boolean emailValido = false;

        while (!emailValido) {
            System.out.print("Digite o email: ");
            email = scanner.nextLine();

            if (email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$")) {
                emailValido = true;
            } else {
                System.out.println("Formato de e-mail invalido. Digite novamente.");
            }
        }

        System.out.print("Digite a senha: ");
        String senha = scanner.nextLine();

        // Criar e retornar um novo usuário
        Usuario novoUsuario = new Usuario(nome, email, senha);
        listaUsuarios.add(novoUsuario);
        return novoUsuario;
    }

    public void convidarUsuarioParaEvento(Usuario usuario, Evento evento) {
        evento.convidarUsuario(usuario);
        Notificacao notificacao = new Notificacao(usuario, "Voce foi convidado para o evento: " + evento.getDescricao());
        notificacao.setRemetente(this); // Define o remetente da notificação como o usuário que convidou
        notificacao.notificarEvento(evento); // Envia notificação para o usuário convidado
    }

    public static Usuario realizarLogin(String nome, String senha, List<Usuario> listaUsuarios) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getNome().equals(nome) && usuario.getSenha().equals(senha)) {
                // Retorna o usuário se o nome e senha coincidirem
                return usuario;
            }
        }
        return null;
    }
}
