import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MenuAgenda {

    private Usuario usuarioLogado;
    private Scanner scanner;
    private List<Usuario> listaUsuarios;


    public MenuAgenda(Usuario usuario, List<Usuario> listaUsuarios) {
        this.usuarioLogado = usuario;
        this.scanner = new Scanner(System.in);
        this.listaUsuarios = listaUsuarios;
    }

    public void exibirMenu() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("### MENU AGENDA ###");
            System.out.println("1. Visualizar Eventos Agendados");
            System.out.println("2. Criar Evento");
            System.out.println("3. Consultar Agenda Diaria");
            System.out.println("4. Consultar Agenda Semanal");
            System.out.println("5. Consultar Agenda Mensal");
            System.out.println("6. Remover Evento da Agenda");
            System.out.println("7. Exibir Notificacoes");
            System.out.println("8. Limpar Notificacoes");
            System.out.println("0. Sair");

            try {
                System.out.print("Escolha uma opcao: ");
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        visualizarEventosAgendados();
                        break;
                    case 2:
                        criarNovoEvento();
                        break;
                    case 3:
                        consultarAgendaDiaria();
                        break;
                    case 4:
                        consultarAgendaSemanal();
                        break;
                    case 5:
                        consultarAgendaMensal();
                        break;
                    case 6:
                        removerEventoDaAgenda();
                        break;
                    case 7:
                        exibirNotificacoes();
                        break;
                    case 8:
                        limparNotificacoes();
                        break;
                    case 0:
                        System.out.println("Saindo do Menu Agenda.");
                        break;
                    default:
                        System.out.println("Opção invalida. Tente novamente.");
                }
            } catch (InputMismatchException e){
                System.out.println("Entrada invalida. Digite um valor numerico valido.");
                scanner.next();
            }
        }
    }

    private void visualizarEventosAgendados() {
        System.out.println("### EVENTOS AGENDADOS ###");
        usuarioLogado.atualizarEventosAgendados(); // Método para atualizar os eventos agendados
        List<Evento> eventosAgendados = usuarioLogado.consultarEventosConvidados();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        for (Evento evento : eventosAgendados) {
            String dataFormatada = evento.getData().format(formatter);
            System.out.println(evento.getDescricao() + " - " + dataFormatada);
        }
    }

    private void criarNovoEvento() {
        System.out.println("### CRIAR NOVO EVENTO ###");
        System.out.print("Descricao: ");
        String descricao = scanner.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate data = null;

        while (data == null) {
            try {
                System.out.print("Data (dd-MM-yyyy): ");
                String dataString = scanner.nextLine();
                data = LocalDate.parse(dataString, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data errado, digite novamente!");
            }
        }

        DateTimeFormatter formatterOutput = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dataFormatada = formatterOutput.format(data);

        Evento novoEvento = null;

        System.out.println("Tipo de Evento:");
        System.out.println("1. Reuniao Virtual");
        System.out.println("2. Reuniao Presencial");
        int opcaoTipoEvento;

        do {
        System.out.print("Escolha o tipo de evento: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Opcao invalida, digite novamente!");
                System.out.print("Escolha o tipo de evento: ");
                scanner.next();
            }

          opcaoTipoEvento = scanner.nextInt();
            if (opcaoTipoEvento < 1 || opcaoTipoEvento > 2) {
                System.out.println("Opcao invalida, digite novamente!");
            }
        } while (opcaoTipoEvento < 1 || opcaoTipoEvento > 2);
        scanner.nextLine();

        switch (opcaoTipoEvento) {
            case 1:
                List<String> equipamentos = new ArrayList<>();
                // Lógica para seleção de equipamentos para a reunião virtual
                equipamentos.add("Camera");
                equipamentos.add("Microfone");
                novoEvento = new ReuniaoVirtual(descricao, dataFormatada, equipamentos);
                double custoReuniaoVirtual = novoEvento.calcularCusto();
                usuarioLogado.getAgendaPessoal().adicionarEventoCriado(novoEvento);
                novoEvento.setCriador(usuarioLogado); // Define o criador do evento como o usuário logado
                System.out.println("Custo total do evento de Reuniao Virtual: $" + custoReuniaoVirtual);
                break;

            case 2:
                List<String> itensLocacao = new ArrayList<>();
                // Lógica para seleção de itens de locação para a reunião presencial
                itensLocacao.add("Sala de Reuniao");
                itensLocacao.add("Cafe e Agua");
                itensLocacao.add("Projetor de Video");
                novoEvento = new ReuniaoPresencial(descricao, dataFormatada, itensLocacao);
                double custoReuniaoPresencial = novoEvento.calcularCusto();
                usuarioLogado.getAgendaPessoal().adicionarEventoCriado(novoEvento);
                novoEvento.setCriador(usuarioLogado); // Define o criador do evento como o usuário logado
                System.out.println("Custo total do evento de Reuniao Presencial: $" + custoReuniaoPresencial);
                break;

            default:
                System.out.println("Opcao invalida para tipo de evento.");
                return;
        }

        System.out.println("Evento criado com sucesso!");

        System.out.print("Deseja convidar outros usuarios para este evento? (S/N): ");
        String resposta = scanner.nextLine();
        if (resposta.equalsIgnoreCase("S")) {
            conviteParaEvento(novoEvento);
        }
    }

    private void consultarAgendaDiaria() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        System.out.println("### CONSULTAR AGENDA DIARIA ###");
        String dataString = null;
        LocalDate data = null;

        while (data == null) {
            try {
                System.out.print("Data (dd-MM-yyyy): ");
                dataString = scanner.nextLine();
                data = LocalDate.parse(dataString, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data errado, digite novamente!");
            }
        }

        List<Evento> eventosDiarios = usuarioLogado.getAgendaPessoal().consultarAgendaDiaria(data);
        if (eventosDiarios.isEmpty()) {
            System.out.println("Nenhum evento agendado para o dia " + data.format(formatter));
        } else {
            System.out.println("Eventos agendados para o dia " + data.format(formatter) + ":");
            for (Evento evento : eventosDiarios) {
                System.out.println(evento.getDescricao() + " - " + evento.getData().format(formatter));

                List<Usuario> usuariosConvidados = encontrarUsuariosDisponiveis(evento);
                for (Usuario usuario : usuariosConvidados) {
                    System.out.println("Convidado: " + usuario.getNome());
                }
            }
        }
    }

    private void consultarAgendaSemanal() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        System.out.println("### CONSULTAR AGENDA SEMANAL ###");
        LocalDate dataInicial = null;
        LocalDate dataFinal = null;

        while (dataInicial == null || dataFinal == null) {
            try {
                System.out.print("Data Inicial (dd-MM-yyyy): ");
                String dataInicialString = scanner.nextLine();
                dataInicial = LocalDate.parse(dataInicialString, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data errado, digite novamente!");
            }

            try {
                System.out.print("Data Final (dd-MM-yyyy): ");
                String dataFinalString = scanner.nextLine();
                dataFinal = LocalDate.parse(dataFinalString, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data errado, digite novamente!");
            }
        }

        List<Evento> eventosSemanais = usuarioLogado.getAgendaPessoal().consultarAgendaSemanal(dataInicial, dataFinal);
        if (eventosSemanais.isEmpty()) {
            System.out.println("Nenhum evento agendado para o periodo de " + dataInicial.format(formatter) + " a " + dataFinal.format(formatter));
        } else {
            System.out.println("Eventos agendados para o periodo de " + dataInicial.format(formatter) + " a " + dataFinal.format(formatter) + ":");
            for (Evento evento : eventosSemanais) {
                System.out.println(evento.getDescricao() + " - " + evento.getData().format(formatter));

                List<Usuario> usuariosConvidados = encontrarUsuariosDisponiveis(evento);
                for (Usuario usuario : usuariosConvidados) {
                    System.out.println("Convidado: " + usuario.getNome());
                }
            }
        }
    }

    private void consultarAgendaMensal() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        System.out.println("### CONSULTAR AGENDA MENSAL ###");
        int mes = 0;
        int ano = 0;

        while (mes < 1 || mes > 12) {
            System.out.print("Mes (1-12): ");
            if (scanner.hasNextInt()) {
                mes = scanner.nextInt();
                if (mes < 1 || mes > 12) {
                    System.out.println("Mes inválido. Digite um numero entre 1 e 12.");
                }
            } else {
                System.out.println("Mes inválido. Digite um numero entre 1 e 12.");
                scanner.next();
            }
            scanner.nextLine();
        }

        while (ano < 1000 || ano > 9999) {
            System.out.print("Ano (YYYY): ");
            if (scanner.hasNextInt()) {
                ano = scanner.nextInt();
                if (ano < 1000 || ano > 9999) {
                    System.out.println("Ano inválido. Digite um número de 4 dígitos.");
                }
            } else {
                System.out.println("Ano inválido. Digite um número de 4 dígitos.");
                scanner.next();
            }
            scanner.nextLine(); // Limpa o buffer
        }

        List<Evento> eventosMensais = usuarioLogado.getAgendaPessoal().consultarAgendaMensal(mes, ano);
        if (eventosMensais.isEmpty()) {
            System.out.println("Nenhum evento agendado para o mes " + mes + " de " + ano);
        } else {
            System.out.println("Eventos agendados para o mes " + mes + " de " + ano + ":");
            for (Evento evento : eventosMensais) {
                System.out.println(evento.getDescricao() + " - " + evento.getData().format(formatter));

                List<Usuario> usuariosConvidados = encontrarUsuariosDisponiveis(evento);
                for (Usuario usuario : usuariosConvidados) {
                    System.out.println("Convidado: " + usuario.getNome());
                }
            }
        }
    }

    private List<Usuario> encontrarUsuariosDisponiveis(Evento evento) {
        List<Usuario> usuariosDisponiveis = new ArrayList<>();

        for (Usuario usuario : listaUsuarios) {
            if (evento.getConvidados().contains(usuario)) {
                usuariosDisponiveis.add(usuario);
            }
        }
        return usuariosDisponiveis;
    }

    private void conviteParaEvento(Evento evento) {
        // Receber convites para um evento específico
        System.out.println("### CONVITE PARA EVENTO ###");
        for (Usuario usuario : listaUsuarios) {
            if (!usuario.equals(usuarioLogado)) { // Verifica se não é o próprio usuário logado
                System.out.println("Convidar " + usuario.getNome() + "? (S/N)");
                String resposta = scanner.nextLine();
                if (resposta.equalsIgnoreCase("S")) {
                    usuario.convidarUsuarioParaEvento(usuario, evento);
                    System.out.println("Convite enviado para " + usuario.getNome());
                }
            }
        }
    }

    private void removerEventoDaAgenda() {
        visualizarEventosAgendados();
        System.out.print("Digite a descricao do evento a ser removido: ");
        String descricao = scanner.nextLine();

        for (Evento evento : usuarioLogado.getAgendaPessoal().getEventosAgendados()) {
            if (evento.getDescricao().equals(descricao)) {
                usuarioLogado.removerEventoDaAgenda(evento);
                System.out.println("Evento removido com sucesso!");
                return;
            }
        }

        System.out.println("Evento não encontrado na agenda.");
    }

    private void exibirNotificacoes() {
        List<Notificacao> notificacoes = usuarioLogado.getNotificacoesRecebidas();
        System.out.println("### NOTIFICACOES ###");
        if (notificacoes.isEmpty()) {
            System.out.println("Nao ha notificacoes.");
        } else {
            for (Notificacao notificacao : notificacoes) {
                System.out.println(notificacao.getMensagem());
            }
        }
    }

    private void limparNotificacoes() {
        usuarioLogado.limparNotificacoes();
        System.out.println("Notificacoes limpas.");
    }
}
