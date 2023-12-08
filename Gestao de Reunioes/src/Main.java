import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Usuario> listaUsuarios = new ArrayList<>();
        Usuario usuarioLogado = null;

        int opcao = 0;
        do {
            System.out.println("### SISTEMA DE AGENDAMENTO ###");
            System.out.println("1. Login");
            System.out.println("2. Cadastrar Usuario");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opcao: ");
            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Entrada invalida. Digite um valor numerico valido.");
                scanner.nextLine();
            }

            switch (opcao) {
                case 1:
                    System.out.print("Digite o nome do usuario: ");
                    String nome = scanner.nextLine();
                    System.out.print("Digite a senha: ");
                    String senha = scanner.nextLine();

                    usuarioLogado = Usuario.realizarLogin(nome, senha, listaUsuarios);
                    if (usuarioLogado != null) {
                        MenuAgenda menu = new MenuAgenda(usuarioLogado,listaUsuarios); //instacia
                        menu.exibirMenu();
                    } else {
                        System.out.println("Usuario nao encontrado ou senha incorreta.");
                    }
                    break;
                case 2:
                    System.out.println("### CADASTRAR NOVO USUARIO ###");
                    boolean nomeExistente;

                    do {
                        nomeExistente = false;
                    Usuario novoUsuario = Usuario.cadastrarUsuario(scanner);

                    // Verificar se o nome já existe na lista de usuários
                        for (Usuario usuario : listaUsuarios) {
                            if (usuario.getNome().equals(novoUsuario.getNome())) {
                                nomeExistente = true;
                                System.out.println("Usuario ja existe. Informe um nome diferente.");
                                break;
                            }
                        }

                        if (!nomeExistente) {
                            listaUsuarios.add(novoUsuario);
                            System.out.println("Usuario cadastrado com sucesso: " + novoUsuario.getNome());
                        }
                    } while (nomeExistente);

                    break;
                case 3:
                    System.out.println("Saindo do sistema.");
                    break;
                default:
                    System.out.println("Opcao invalida.");
                    break;
            }
        } while (opcao != 3);

        scanner.close();
    }
}
