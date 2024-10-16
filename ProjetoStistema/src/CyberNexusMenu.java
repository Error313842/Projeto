import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import projetostistema.Produto;

public class CyberNexusMenu extends JFrame {

    private static final ArrayList<Produto> produtos = new ArrayList<>();
    private static final String FILE_NAME = "produtos.dat"; // Nome do arquivo para salvar produtos

    public CyberNexusMenu() {
        carregarProdutos(); // Carrega os produtos ao iniciar

        setTitle("CyberNexus - Plataforma Virtual");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Painel de fundo
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(new ImageIcon("caminho/para/sua/imagem.jpg").getImage(), 0, 0, null);
            }
        };
        panel.setLayout(new GridBagLayout());
        add(panel);

        // Botões
        JButton cadastrarButton = new JButton("Cadastrar Produto");
        JButton servicosButton = new JButton("Saber mais sobre Serviços");
        JButton listarButton = new JButton("Listar Produtos Cadastrados");
        JButton sairButton = new JButton("Sair");

        // Estilizando os botões
        styleButton(cadastrarButton);
        styleButton(servicosButton);
        styleButton(listarButton);
        styleButton(sairButton);

        // Adicionando ação aos botões
        cadastrarButton.addActionListener(e -> cadastrarProduto());
        servicosButton.addActionListener(e -> exibirInformacoesSobreServicos());
        listarButton.addActionListener(e -> listarProdutos());
        sairButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Você realmente deseja sair?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                salvarProdutos(); // Salva os produtos ao sair
                System.exit(0);
            }
        });

        // Organizando os botões no painel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        for (Component button : new Component[]{cadastrarButton, servicosButton, listarButton, sairButton}) {
            button.setPreferredSize(new Dimension(250, 50));
            panel.add(button, gbc);
            gbc.gridy++;
        }
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(Color.BLUE);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEtchedBorder());
    }

    private void cadastrarProduto() {
        String nome = JOptionPane.showInputDialog(this, "Nome:");
        if (nome == null || nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome não pode ser vazio.");
            return;
        }

        String descricao = JOptionPane.showInputDialog(this, "Descrição:");
        if (descricao == null || descricao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Descrição não pode ser vazia.");
            return;
        }

        String precoStr = JOptionPane.showInputDialog(this, "Preço:");
        try {
            double preco = Double.parseDouble(precoStr);
            if (preco < 0) {
                JOptionPane.showMessageDialog(this, "Preço não pode ser negativo.");
                return;
            }
            Produto produto = new Produto(nome, descricao, preco);
            produtos.add(produto);
            JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Preço inválido. Tente novamente.");
        }
    }

    private void listarProdutos() {
        if (produtos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum produto cadastrado.");
            return;
        }

        // Criar um campo de texto para o filtro
        JTextField filterField = new JTextField(20);
        JPanel panel = new JPanel();
        panel.add(new JLabel("Filtrar por TI:"));
        panel.add(filterField);

        // Exibir o campo de texto para o usuário
        int result = JOptionPane.showConfirmDialog(this, panel, "Listar Produtos", JOptionPane.OK_CANCEL_OPTION);
        if (result != JOptionPane.OK_OPTION) {
            return; // Se o usuário cancelar, sair do método
        }

        String filtro = filterField.getText().toLowerCase();
        StringBuilder listaFiltrada = new StringBuilder();

        for (Produto produto : produtos) {
            if (produto.getNome().toLowerCase().contains(filtro) || 
                produto.getDescricao().toLowerCase().contains(filtro)) {
                listaFiltrada.append("Nome: ").append(produto.getNome()).append("\n")
                             .append("Descrição: ").append(produto.getDescricao()).append("\n")
                             .append("Preço: R$ ").append(produto.getPreco()).append("\n")
                             .append("\n"); // Espaço entre produtos
            }
        }

        if (listaFiltrada.length() == 0) {
            JOptionPane.showMessageDialog(this, "Nenhum produto encontrado com o filtro: " + filtro);
        } else {
            JTextArea textArea = new JTextArea(listaFiltrada.toString());
            textArea.setEditable(false);
            textArea.setLineWrap(true); // Para quebrar linhas automaticamente
            textArea.setWrapStyleWord(true); // Para quebrar palavras corretamente
            JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "Lista de Produtos Filtrados", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void exibirInformacoesSobreServicos() {
        String informacoes = """
            A CyberNexus é uma empresa comprometida em oferecer serviços de alta qualidade
            para todos os seus clientes. Nossa equipe está dedicada a proporcionar soluções 
            inovadoras e personalizadas, sempre com foco na satisfação do cliente.
            
            Nosso empenho em entender as necessidades de cada cliente nos diferencia, 
            garantindo que cada solução seja feita sob medida. Trabalhamos com paixão, 
            buscando sempre superar expectativas e entregar resultados que fazem a diferença.
            
            Com a CyberNexus, você pode contar com profissionalismo, atenção aos detalhes 
            e um suporte contínuo para atender às suas necessidades, a qualquer momento.
            """;
        JOptionPane.showMessageDialog(this, informacoes);
    }

    private void salvarProdutos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(produtos);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar produtos: " + e.getMessage());
        }
    }

    private void carregarProdutos() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                ArrayList<Produto> produtosCarregados = (ArrayList<Produto>) ois.readObject();
                produtos.addAll(produtosCarregados);
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Erro ao carregar produtos: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CyberNexusMenu menu = new CyberNexusMenu();
            menu.setVisible(true);
        });
    }
}
