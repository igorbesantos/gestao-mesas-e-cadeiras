package br.com.ibssoft.gestao;
//TODO N+1 Database problem
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.GregorianCalendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;

import br.com.ibssoft.database.ClientesDAO;
import br.com.ibssoft.database.ConnectionPool;
import br.com.ibssoft.gestao.aluguel.DiaAluguel;
import br.com.ibssoft.gestao.cliente.Cliente;

public class GestaoFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private boolean isEntrada;
	
	private JPanel contentPane;
	private CardLayout layoutController = new CardLayout(0,0);
	private CardLayout panelEstOpLayout;
	private JTextField estEOutroPicker;
	private JTextField estSOutroPicker;
	private JTextField txtNome;
	private JTextField txtTel;
	private JPanel panelWorkstation;
	private JPanel homePanel;
	private JPanel estoquePanel;
	private JPanel clientePanel;
	private JPanel panelEstOp;
	private JPanel panelClientCard;
	private JPanel panelListaCliente;
	private JLabel subTitulo;
	private JLabel totJogLbl = new JLabel("0");
	private JLabel totCadLbl = new JLabel("0");
	private JLabel totMesLbl = new JLabel("0");
	private JLabel mesDispLbl = new JLabel("0");
	private JLabel cadDispLbl = new JLabel("0");
	private JLabel jogDispLbl = new JLabel("0");
	private JLabel totCliLbl = new JLabel("0");
	private Connection connection;
	private ClientesDAO clientesDAO;
	private DiaAluguel diaAluguel;
	private JButton btnEstInicio;
	private JButton btnEstAnterior;
	private JButton btnEstSalvar;
	private JButton btnOp1Entrada;
	private JButton btnOp1Saida;
	private JButton gestaoEstoqueBtn;
	private JButton gestaoClienteBtn;
	private JButton btnAdicionarCliente;
	private JButton btnRemoverCliente;
	private JButton btnCliAnterior;
	private JButton btnCadastrarCliente;
	private JComboBox<String> estSProdutoPicker;
	private JComboBox<String> estEProdutoPicker; 
	private JSpinner estSQtdPicker;
	private JSpinner estSValorPicker;
	private JSpinner estEQtdPicker;
	private JSpinner estEValorPicker;
	private JCheckBox checkBoxVenda;
	private JCheckBox checkBoxQuebra;
	private JCheckBox checkBoxEOutro;
	private JCheckBox checkBoxSOutro;
	private JCheckBox checkBoxCompra;
	private JTextArea txtrInformaesAqui = new JTextArea();
	private JTextArea txtEnd;
	private JTable tableClientes;
	private JLabel nomeClienteRemovido;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		LocalDate dia = (LocalDate.now());
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GestaoFrame frame = new GestaoFrame(dia);
					//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);  // Maximiza ao iniciar
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GestaoFrame(LocalDate dia) throws SQLException {
		connection = new ConnectionPool().getConnection();
		clientesDAO = new ClientesDAO(connection);
		diaAluguel = DiaAluguel.of(connection, dia);
		dia=null;
		
		setTitle("Gest\u00E3o Mesas e Cadeiras");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		
		contentPane = new JPanel();
		contentPane.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelTitulo = new JPanel();
		panelTitulo.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		contentPane.add(panelTitulo, BorderLayout.NORTH);
		
		JLabel label_13 = new JLabel("Gest\u00E3o Mesas e Cadeiras");
		label_13.setHorizontalAlignment(SwingConstants.CENTER);
		label_13.setFont(new Font("Microsoft YaHei Light", Font.BOLD, 18));
		label_13.setBackground(Color.WHITE);
		panelTitulo.add(label_13);
		
		JPanel panelBtnsAcesso = new JPanel();
		panelBtnsAcesso.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), new BevelBorder(BevelBorder.RAISED, null, null, null, null)));
		contentPane.add(panelBtnsAcesso, BorderLayout.SOUTH);
		
		gestaoEstoqueBtn = new JButton("Gest\u00E3o Estoque");
		gestaoEstoqueBtn.addActionListener(estPanListener);
		gestaoEstoqueBtn.setFont(new Font("Microsoft YaHei Light", Font.PLAIN, 30));
		panelBtnsAcesso.setLayout(new GridLayout(0, 2, 0, 0));
		
		gestaoClienteBtn = new JButton("Gest\u00E3o Cliente");
		gestaoClienteBtn.addActionListener(cliPanListener);
		gestaoClienteBtn.setFont(new Font("Microsoft YaHei Light", Font.PLAIN, 30));
		panelBtnsAcesso.add(gestaoClienteBtn);
		panelBtnsAcesso.add(gestaoEstoqueBtn);
		
		JPanel panelMain = new JPanel();
		panelMain.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.add(panelMain, BorderLayout.CENTER);
		panelMain.setLayout(new BorderLayout(0, 0));
		
		JPanel panelSubTitulo = new JPanel();
		panelSubTitulo.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panelMain.add(panelSubTitulo, BorderLayout.NORTH);
		
		subTitulo = new JLabel("Informa\u00E7\u00F5es de Clientes e Estoque");
		panelSubTitulo.add(subTitulo);
		subTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		subTitulo.setForeground(Color.BLUE);
		subTitulo.setFont(new Font("Microsoft YaHei Light", Font.BOLD, 25));
		

		
		panelWorkstation = new JPanel();
		panelWorkstation.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JPanel panelWorkstationComAviso = new JPanel();
		panelWorkstationComAviso.setLayout(new BorderLayout(0,0));
		panelWorkstationComAviso.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelWorkstationComAviso.add(panelWorkstation, BorderLayout.CENTER);
		
		panelMain.add(panelWorkstationComAviso, BorderLayout.CENTER);
		panelWorkstation.setLayout(layoutController);
		
		updateWorkstation(); //Atualiza os valores dos JLabels
		
		homePanel = new JPanel();
		GridBagLayout gbl_homePanel = new GridBagLayout();
		gbl_homePanel.columnWidths = new int[]{187, 0, 0, 0, 0, 0, 0, 0};
		gbl_homePanel.rowHeights = new int[]{29, 29, 29, 29, 29, 29, 0, 0, 0, 0};
		gbl_homePanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_homePanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		homePanel.setLayout(gbl_homePanel);
		
		JLabel label_3 = new JLabel("Total de Cadeiras:");
		label_3.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 22));
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.gridheight = 2;
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 0;
		gbc_label_3.gridy = 0;
		homePanel.add(label_3, gbc_label_3);
		
		totCadLbl.setForeground(new Color(70, 130, 180));
		totCadLbl.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 22));
		totCadLbl.setBackground(new Color(248, 248, 255));
		GridBagConstraints gbc_totCadLbl = new GridBagConstraints();
		gbc_totCadLbl.gridheight = 2;
		gbc_totCadLbl.insets = new Insets(0, 0, 5, 5);
		gbc_totCadLbl.gridx = 1;
		gbc_totCadLbl.gridy = 0;
		homePanel.add(totCadLbl, gbc_totCadLbl);
		
		JLabel label_14 = new JLabel("                ");
		GridBagConstraints gbc_label_14 = new GridBagConstraints();
		gbc_label_14.gridheight = 6;
		gbc_label_14.gridwidth = 2;
		gbc_label_14.insets = new Insets(0, 0, 5, 5);
		gbc_label_14.gridx = 2;
		gbc_label_14.gridy = 0;
		homePanel.add(label_14, gbc_label_14);
		
		JLabel label_1 = new JLabel("Cadeiras Dispon\u00EDveis:");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.gridheight = 2;
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 4;
		gbc_label_1.gridy = 0;
		homePanel.add(label_1, gbc_label_1);
		label_1.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 22));
		
		GridBagConstraints gbc_cadDispLbl = new GridBagConstraints();
		gbc_cadDispLbl.gridheight = 2;
		gbc_cadDispLbl.insets = new Insets(0, 0, 5, 5);
		gbc_cadDispLbl.gridx = 5;
		gbc_cadDispLbl.gridy = 0;
		homePanel.add(cadDispLbl, gbc_cadDispLbl);
		cadDispLbl.setForeground(new Color(70, 130, 180));
		cadDispLbl.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 22));
		cadDispLbl.setBackground(new Color(248, 248, 255));
		
		JLabel rightBorderEmptyLabel = new JLabel("   ");
		GridBagConstraints gbc_rightBorderEmptyLabel = new GridBagConstraints();
		gbc_rightBorderEmptyLabel.insets = new Insets(0, 0, 5, 0);
		gbc_rightBorderEmptyLabel.gridx = 6;
		gbc_rightBorderEmptyLabel.gridy = 0;
		homePanel.add(rightBorderEmptyLabel, gbc_rightBorderEmptyLabel);
		
		JLabel label_9 = new JLabel("Total de Mesas:");
		label_9.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 22));
		GridBagConstraints gbc_label_9 = new GridBagConstraints();
		gbc_label_9.gridheight = 2;
		gbc_label_9.insets = new Insets(0, 0, 5, 5);
		gbc_label_9.gridx = 0;
		gbc_label_9.gridy = 2;
		homePanel.add(label_9, gbc_label_9);
		
		totMesLbl.setForeground(new Color(70, 130, 180));
		totMesLbl.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 22));
		totMesLbl.setBackground(new Color(248, 248, 255));
		GridBagConstraints gbc_totMesLbl = new GridBagConstraints();
		gbc_totMesLbl.gridheight = 2;
		gbc_totMesLbl.insets = new Insets(0, 0, 5, 5);
		gbc_totMesLbl.gridx = 1;
		gbc_totMesLbl.gridy = 2;
		homePanel.add(totMesLbl, gbc_totMesLbl);
		
		JLabel label_4 = new JLabel("Mesas Dispon\u00EDveis:");
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.gridheight = 2;
		gbc_label_4.insets = new Insets(0, 0, 5, 5);
		gbc_label_4.gridx = 4;
		gbc_label_4.gridy = 2;
		homePanel.add(label_4, gbc_label_4);
		label_4.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 22));
		
		GridBagConstraints gbc_mesDispLbl = new GridBagConstraints();
		gbc_mesDispLbl.gridheight = 2;
		gbc_mesDispLbl.insets = new Insets(0, 0, 5, 5);
		gbc_mesDispLbl.gridx = 5;
		gbc_mesDispLbl.gridy = 2;
		homePanel.add(mesDispLbl, gbc_mesDispLbl);
		mesDispLbl.setForeground(new Color(70, 130, 180));
		mesDispLbl.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 22));
		mesDispLbl.setBackground(new Color(248, 248, 255));
		
		JLabel label_11 = new JLabel("Total de Jogos:");
		label_11.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 22));
		GridBagConstraints gbc_label_11 = new GridBagConstraints();
		gbc_label_11.gridheight = 2;
		gbc_label_11.insets = new Insets(0, 0, 5, 5);
		gbc_label_11.gridx = 0;
		gbc_label_11.gridy = 4;
		homePanel.add(label_11, gbc_label_11);
		panelWorkstation.add(homePanel, "homePanel");
		
		totJogLbl.setForeground(new Color(70, 130, 180));
		totJogLbl.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 22));
		totJogLbl.setBackground(new Color(248, 248, 255));
		GridBagConstraints gbc_totJogLbl = new GridBagConstraints();
		gbc_totJogLbl.gridheight = 2;
		gbc_totJogLbl.insets = new Insets(0, 0, 5, 5);
		gbc_totJogLbl.gridx = 1;
		gbc_totJogLbl.gridy = 4;
		homePanel.add(totJogLbl, gbc_totJogLbl);
		
		JLabel label_6 = new JLabel("Jogos Dispon\u00EDveis:");
		GridBagConstraints gbc_label_6 = new GridBagConstraints();
		gbc_label_6.gridheight = 2;
		gbc_label_6.insets = new Insets(0, 0, 5, 5);
		gbc_label_6.gridx = 4;
		gbc_label_6.gridy = 4;
		homePanel.add(label_6, gbc_label_6);
		label_6.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 22));
		
		GridBagConstraints gbc_jogDispLbl = new GridBagConstraints();
		gbc_jogDispLbl.gridheight = 2;
		gbc_jogDispLbl.insets = new Insets(0, 0, 5, 5);
		gbc_jogDispLbl.gridx = 5;
		gbc_jogDispLbl.gridy = 4;
		homePanel.add(jogDispLbl, gbc_jogDispLbl);
		jogDispLbl.setForeground(new Color(70, 130, 180));
		jogDispLbl.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 22));
		jogDispLbl.setBackground(new Color(248, 248, 255));
		
		JLabel label_15 = new JLabel("Total de Clientes:");
		label_15.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 22));
		GridBagConstraints gbc_label_15 = new GridBagConstraints();
		gbc_label_15.gridheight = 2;
		gbc_label_15.insets = new Insets(0, 0, 5, 5);
		gbc_label_15.gridx = 0;
		gbc_label_15.gridy = 6;
		homePanel.add(label_15, gbc_label_15);
		
		totCliLbl.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 22));
		totCliLbl.setForeground(new Color(70, 130, 180));
		GridBagConstraints gbc_totCliLbl = new GridBagConstraints();
		gbc_totCliLbl.gridheight = 2;
		gbc_totCliLbl.insets = new Insets(0, 0, 5, 5);
		gbc_totCliLbl.gridx = 1;
		gbc_totCliLbl.gridy = 6;
		homePanel.add(totCliLbl, gbc_totCliLbl);
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0){
				diaAluguel = DiaAluguel.of(connection, diaAluguel.getData());
				try {
					updateWorkstation("Informações atualizadas.");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		btnAtualizar.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		GridBagConstraints gbc_btnAtualizar = new GridBagConstraints();
		gbc_btnAtualizar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAtualizar.gridheight = 2;
		gbc_btnAtualizar.insets = new Insets(0, 0, 5, 5);
		gbc_btnAtualizar.gridwidth = 2;
		gbc_btnAtualizar.gridx = 4;
		gbc_btnAtualizar.gridy = 6;
		homePanel.add(btnAtualizar, gbc_btnAtualizar);
		
		clientePanel = new JPanel();
		panelWorkstation.add(clientePanel, "clientePanel");
		clientePanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panelMenEsq = new JPanel();
		panelMenEsq.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		clientePanel.add(panelMenEsq, BorderLayout.WEST);
		GridBagLayout gbl_panelMenEsq = new GridBagLayout();
		gbl_panelMenEsq.columnWidths = new int[]{120, 0};
		gbl_panelMenEsq.rowHeights = new int[]{23, 0, 0, 0, 0, 0};
		gbl_panelMenEsq.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panelMenEsq.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelMenEsq.setLayout(gbl_panelMenEsq);
		
		JButton btnCliInicio = new JButton("In\u00EDcio");
		btnCliInicio.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		GridBagConstraints gbc_btnCliInicio = new GridBagConstraints();
		gbc_btnCliInicio.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCliInicio.insets = new Insets(0, 0, 5, 0);
		gbc_btnCliInicio.gridx = 0;
		gbc_btnCliInicio.gridy = 0;
		panelMenEsq.add(btnCliInicio, gbc_btnCliInicio);
		
		btnCliAnterior = new JButton("Anterior");
		btnCliAnterior.addActionListener(btnCliAnteriorListener);
		btnCliAnterior.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		GridBagConstraints gbc_btnCliAnterior = new GridBagConstraints();
		gbc_btnCliAnterior.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCliAnterior.insets = new Insets(0, 0, 5, 0);
		gbc_btnCliAnterior.gridx = 0;
		gbc_btnCliAnterior.gridy = 1;
		panelMenEsq.add(btnCliAnterior, gbc_btnCliAnterior);
		btnCliInicio.addActionListener(homPanListener);
		
		panelClientCard = new JPanel();
		panelClientCard.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		clientePanel.add(panelClientCard, BorderLayout.CENTER);
		panelClientCard.setLayout(new CardLayout(0, 0));
		
		JPanel panCli = new JPanel();
		panCli.setBorder(null);
		panelClientCard.add(panCli, "panCli");
		panCli.setLayout(new BorderLayout(0, 0));
		
		panelListaCliente = new JPanel();
		panCli.add(panelListaCliente, BorderLayout.CENTER);
		panelListaCliente.setLayout(new BorderLayout(0, 0));

		updateTableClientes();
		
		
		JPanel panelAddRemMenu = new JPanel();
		panCli.add(panelAddRemMenu, BorderLayout.SOUTH);
		panelAddRemMenu.setLayout(new GridLayout(0, 2, 0, 0));
		
		btnAdicionarCliente = new JButton("Adicionar Cliente");
		panelAddRemMenu.add(btnAdicionarCliente);
		btnAdicionarCliente.addActionListener(btnAdicionarClienteListener);
		btnAdicionarCliente.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		
		btnRemoverCliente = new JButton("Remover Cliente");
		panelAddRemMenu.add(btnRemoverCliente);
		btnRemoverCliente.addActionListener(btnRemoverClienteListener);
		btnRemoverCliente.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		
		JPanel panelAddCliente = new JPanel();
		panelClientCard.add(panelAddCliente, "panAddCli");
		GridBagLayout gbl_panelAddCliente = new GridBagLayout();
		gbl_panelAddCliente.columnWidths = new int[]{426, 0};
		gbl_panelAddCliente.rowHeights = new int[]{40, 40, 80, 0, 0};
		gbl_panelAddCliente.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panelAddCliente.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelAddCliente.setLayout(gbl_panelAddCliente);
		
		JPanel panelNom = new JPanel();
		panelNom.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagLayout gbl_panelNom = new GridBagLayout();
		gbl_panelNom.columnWidths = new int[]{70, 348, 0};
		gbl_panelNom.rowHeights = new int[]{29, 0};
		gbl_panelNom.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panelNom.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelNom.setLayout(gbl_panelNom);
		
		JLabel lblNome = new JLabel("Nome:    ");
		GridBagConstraints gbc_lblNome = new GridBagConstraints();
		gbc_lblNome.fill = GridBagConstraints.VERTICAL;
		gbc_lblNome.anchor = GridBagConstraints.WEST;
		gbc_lblNome.insets = new Insets(0, 0, 0, 5);
		gbc_lblNome.gridx = 0;
		gbc_lblNome.gridy = 0;
		panelNom.add(lblNome, gbc_lblNome);
		lblNome.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 22));
		GridBagConstraints gbc_panelNom = new GridBagConstraints();
		gbc_panelNom.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelNom.insets = new Insets(0, 0, 5, 0);
		gbc_panelNom.gridx = 0;
		gbc_panelNom.gridy = 0;
		panelAddCliente.add(panelNom, gbc_panelNom);
		
		txtNome = new JTextField();
		GridBagConstraints gbc_txtNome = new GridBagConstraints();
		gbc_txtNome.fill = GridBagConstraints.BOTH;
		gbc_txtNome.gridx = 1;
		gbc_txtNome.gridy = 0;
		panelNom.add(txtNome, gbc_txtNome);
		txtNome.setFont(new Font("Dialog", Font.PLAIN, 16));
		txtNome.setColumns(10);
		
		JPanel panelTel = new JPanel();
		panelTel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagLayout gbl_panelTel = new GridBagLayout();
		gbl_panelTel.columnWidths = new int[]{96, 351, 0};
		gbl_panelTel.rowHeights = new int[]{29, 0};
		gbl_panelTel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panelTel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelTel.setLayout(gbl_panelTel);
		
		JLabel lblTelefone = new JLabel("Tel:        ");
		lblTelefone.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblTelefone = new GridBagConstraints();
		gbc_lblTelefone.anchor = GridBagConstraints.WEST;
		gbc_lblTelefone.insets = new Insets(0, 0, 0, 5);
		gbc_lblTelefone.gridx = 0;
		gbc_lblTelefone.gridy = 0;
		panelTel.add(lblTelefone, gbc_lblTelefone);
		lblTelefone.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 22));
		
		txtTel = new JTextField();
		GridBagConstraints gbc_txtTel = new GridBagConstraints();
		gbc_txtTel.fill = GridBagConstraints.BOTH;
		gbc_txtTel.gridx = 1;
		gbc_txtTel.gridy = 0;
		panelTel.add(txtTel, gbc_txtTel);
		txtTel.setFont(new Font("Dialog", Font.PLAIN, 16));
		txtTel.setColumns(10);
		GridBagConstraints gbc_panelTel = new GridBagConstraints();
		gbc_panelTel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelTel.insets = new Insets(0, 0, 5, 0);
		gbc_panelTel.gridx = 0;
		gbc_panelTel.gridy = 1;
		panelAddCliente.add(panelTel, gbc_panelTel);
		
		JPanel panelEnd = new JPanel();
		panelEnd.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagLayout gbl_panelEnd = new GridBagLayout();
		gbl_panelEnd.columnWidths = new int[]{102, 342, 0};
		gbl_panelEnd.rowHeights = new int[]{70, 0};
		gbl_panelEnd.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panelEnd.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelEnd.setLayout(gbl_panelEnd);
		
		JLabel lblEndereo = new JLabel("End.:");
		lblEndereo.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblEndereo = new GridBagConstraints();
		gbc_lblEndereo.fill = GridBagConstraints.VERTICAL;
		gbc_lblEndereo.anchor = GridBagConstraints.WEST;
		gbc_lblEndereo.insets = new Insets(0, 0, 0, 5);
		gbc_lblEndereo.gridx = 0;
		gbc_lblEndereo.gridy = 0;
		panelEnd.add(lblEndereo, gbc_lblEndereo);
		lblEndereo.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 22));
		
		txtEnd = new JTextArea();
		GridBagConstraints gbc_txtEnd = new GridBagConstraints();
		gbc_txtEnd.fill = GridBagConstraints.BOTH;
		gbc_txtEnd.gridx = 1;
		gbc_txtEnd.gridy = 0;
		panelEnd.add(txtEnd, gbc_txtEnd);
		txtEnd.setFont(new Font("Dialog", Font.PLAIN, 16));
		GridBagConstraints gbc_panelEnd = new GridBagConstraints();
		gbc_panelEnd.insets = new Insets(0, 0, 5, 0);
		gbc_panelEnd.fill = GridBagConstraints.BOTH;
		gbc_panelEnd.gridx = 0;
		gbc_panelEnd.gridy = 2;
		panelAddCliente.add(panelEnd, gbc_panelEnd);
		
		btnCadastrarCliente = new JButton("Cadastrar Cliente");
		btnCadastrarCliente.addActionListener(btnCadastrarClienteListener);
		GridBagConstraints gbc_btnCadastrarCliente = new GridBagConstraints();
		gbc_btnCadastrarCliente.gridx = 0;
		gbc_btnCadastrarCliente.gridy = 3;
		panelAddCliente.add(btnCadastrarCliente, gbc_btnCadastrarCliente);
		
		JPanel panelRemCliente = new JPanel();
		panelClientCard.add(panelRemCliente, "panRemCli");
		panelRemCliente.setLayout(new BorderLayout(0, 0));
		
		JLabel lblDesejaRemoverO = new JLabel("Deseja Remover o Cliente citado abaixo?");
		lblDesejaRemoverO.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		lblDesejaRemoverO.setHorizontalAlignment(SwingConstants.CENTER);
		panelRemCliente.add(lblDesejaRemoverO, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		panelRemCliente.add(panel, BorderLayout.WEST);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		panelRemCliente.add(splitPane, BorderLayout.SOUTH);
		
		JButton btnRemoverSim = new JButton("Sim");
		btnRemoverSim.addActionListener(btnRemoverSimListener );
		btnRemoverSim.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		splitPane.setLeftComponent(btnRemoverSim);
		
		JButton btnRemoverNo = new JButton("N\u00E3o");
		btnRemoverNo.addActionListener(btnCliAnteriorListener);
		btnRemoverNo.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		splitPane.setRightComponent(btnRemoverNo);
		
		nomeClienteRemovido = new JLabel("");
		nomeClienteRemovido.setHorizontalAlignment(SwingConstants.CENTER);
		panelRemCliente.add(nomeClienteRemovido, BorderLayout.CENTER);
		
		estoquePanel = new JPanel();
		panelWorkstation.add(estoquePanel, "estoquePanel");
		estoquePanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panelEstMenu = new JPanel();
		panelEstMenu.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		estoquePanel.add(panelEstMenu, BorderLayout.WEST);
		GridBagLayout gbl_panelEstMenu = new GridBagLayout();
		gbl_panelEstMenu.columnWidths = new int[]{120, 0};
		gbl_panelEstMenu.rowHeights = new int[]{30, 30, 30, 0};
		gbl_panelEstMenu.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panelEstMenu.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelEstMenu.setLayout(gbl_panelEstMenu);
		
		btnEstInicio = new JButton("In\u00EDcio");
		btnEstInicio.addActionListener(homPanListener);
		btnEstInicio.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		GridBagConstraints gbc_btnEstInicio = new GridBagConstraints();
		gbc_btnEstInicio.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnEstInicio.insets = new Insets(0, 0, 5, 0);
		gbc_btnEstInicio.gridx = 0;
		gbc_btnEstInicio.gridy = 0;
		panelEstMenu.add(btnEstInicio, gbc_btnEstInicio);
		
		btnEstAnterior = new JButton("Anterior");
		btnEstAnterior.addActionListener(estAnteriorListener);
		btnEstAnterior.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		GridBagConstraints gbc_btnEstAnterior = new GridBagConstraints();
		gbc_btnEstAnterior.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnEstAnterior.insets = new Insets(0, 0, 5, 0);
		gbc_btnEstAnterior.gridx = 0;
		gbc_btnEstAnterior.gridy = 1;
		panelEstMenu.add(btnEstAnterior, gbc_btnEstAnterior);
		
		btnEstSalvar = new JButton("Salvar");
		btnEstSalvar.addActionListener(estExecListener);
		btnEstSalvar.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		GridBagConstraints gbc_btnEstSalvar = new GridBagConstraints();
		gbc_btnEstSalvar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnEstSalvar.gridx = 0;
		gbc_btnEstSalvar.gridy = 2;
		panelEstMenu.add(btnEstSalvar, gbc_btnEstSalvar);
		
		panelEstOp = new JPanel();
		panelEstOp.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		estoquePanel.add(panelEstOp, BorderLayout.CENTER);
		panelEstOpLayout= new CardLayout(0,0);
		panelEstOp.setLayout(panelEstOpLayout);
		
		JPanel panelEstOp1 = new JPanel();
		panelEstOp.add(panelEstOp1, "name_131903174404027");
		
		btnOp1Entrada = new JButton("Entrada no Estoque");
		btnOp1Entrada.addActionListener(btnOp1EntradaListener);
		btnOp1Saida = new JButton("Sa\u00EDda no Estoque");
		btnOp1Saida.addActionListener(btnOp1SaidaListener);
		panelEstOp1.setLayout(new GridLayout(0, 2, 0, 0));
		panelEstOp1.add(btnOp1Entrada);
		panelEstOp1.add(btnOp1Saida);
		
		JPanel panelSaidaEstoque = new JPanel();
		panelEstOp.add(panelSaidaEstoque, "saidaEstoque");
		GridBagLayout gbl_panelSaidaEstoque = new GridBagLayout();
		gbl_panelSaidaEstoque.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panelSaidaEstoque.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelSaidaEstoque.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panelSaidaEstoque.rowWeights = new double[]{1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		panelSaidaEstoque.setLayout(gbl_panelSaidaEstoque);
		
		JPanel panelSaidPrd = new JPanel();
		GridBagConstraints gbc_panelSaidPrd = new GridBagConstraints();
		gbc_panelSaidPrd.gridwidth = 3;
		gbc_panelSaidPrd.insets = new Insets(0, 0, 5, 0);
		gbc_panelSaidPrd.fill = GridBagConstraints.BOTH;
		gbc_panelSaidPrd.gridx = 0;
		gbc_panelSaidPrd.gridy = 0;
		panelSaidaEstoque.add(panelSaidPrd, gbc_panelSaidPrd);
		panelSaidPrd.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblProduto = new JLabel("Produto:");
		lblProduto.setHorizontalAlignment(SwingConstants.CENTER);
		lblProduto.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		panelSaidPrd.add(lblProduto);
		
		estSProdutoPicker = new JComboBox<String>();
		estSProdutoPicker.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));
		estSProdutoPicker.setToolTipText("Escolha uma unidade");
		estSProdutoPicker.setModel(new DefaultComboBoxModel<String>(new String[] {"Mesas", "Cadeiras", "Jogos"}));
		estSProdutoPicker.setMaximumRowCount(3);
		panelSaidPrd.add(estSProdutoPicker);
		
		JSeparator separator_5 = new JSeparator();
		GridBagConstraints gbc_separator_5 = new GridBagConstraints();
		gbc_separator_5.gridwidth = 3;
		gbc_separator_5.insets = new Insets(0, 0, 5, 5);
		gbc_separator_5.gridx = 0;
		gbc_separator_5.gridy = 1;
		panelSaidaEstoque.add(separator_5, gbc_separator_5);
		
		JPanel panelSaidQtd = new JPanel();
		panelSaidQtd.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_panelSaidQtd = new GridBagConstraints();
		gbc_panelSaidQtd.gridwidth = 3;
		gbc_panelSaidQtd.insets = new Insets(0, 0, 5, 0);
		gbc_panelSaidQtd.fill = GridBagConstraints.BOTH;
		gbc_panelSaidQtd.gridx = 0;
		gbc_panelSaidQtd.gridy = 2;
		panelSaidaEstoque.add(panelSaidQtd, gbc_panelSaidQtd);
		panelSaidQtd.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel label_5 = new JLabel("Quantidade:");
		label_5.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		panelSaidQtd.add(label_5);
		
		estSQtdPicker = new JSpinner();
		estSQtdPicker.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		estSQtdPicker.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));
		panelSaidQtd.add(estSQtdPicker);
		
		JSeparator separator_1 = new JSeparator();
		GridBagConstraints gbc_separator_1 = new GridBagConstraints();
		gbc_separator_1.gridwidth = 3;
		gbc_separator_1.insets = new Insets(0, 0, 5, 0);
		gbc_separator_1.gridx = 0;
		gbc_separator_1.gridy = 3;
		panelSaidaEstoque.add(separator_1, gbc_separator_1);
		
		JPanel panelSaidMot = new JPanel();
		panelSaidMot.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_panelSaidMot = new GridBagConstraints();
		gbc_panelSaidMot.gridheight = 7;
		gbc_panelSaidMot.gridwidth = 3;
		gbc_panelSaidMot.fill = GridBagConstraints.BOTH;
		gbc_panelSaidMot.gridx = 0;
		gbc_panelSaidMot.gridy = 4;
		panelSaidaEstoque.add(panelSaidMot, gbc_panelSaidMot);
		GridBagLayout gbl_panelSaidMot = new GridBagLayout();
		gbl_panelSaidMot.columnWidths = new int[]{38, 36, 55, 0};
		gbl_panelSaidMot.rowHeights = new int[]{23, 0, 0, 0, 0, 0};
		gbl_panelSaidMot.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panelSaidMot.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelSaidMot.setLayout(gbl_panelSaidMot);
		
		JLabel label_7 = new JLabel("Motivo:");
		label_7.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		GridBagConstraints gbc_label_7 = new GridBagConstraints();
		gbc_label_7.gridheight = 5;
		gbc_label_7.insets = new Insets(0, 0, 0, 5);
		gbc_label_7.gridx = 0;
		gbc_label_7.gridy = 0;
		panelSaidMot.add(label_7, gbc_label_7);
		
		checkBoxVenda = new JCheckBox("Venda");
		checkBoxVenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkBoxVenda.isSelected()){
					checkBoxQuebra.setEnabled(false);
					checkBoxSOutro.setEnabled(false);
					estSOutroPicker.setEnabled(false);
					estSValorPicker.setEnabled(true);
				}else{
					checkBoxQuebra.setEnabled(true);
					checkBoxSOutro.setEnabled(true);
					estSValorPicker.setEnabled(false);
				}
			}
		});
		
		checkBoxVenda.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));
		GridBagConstraints gbc_checkBoxVenda = new GridBagConstraints();
		gbc_checkBoxVenda.gridwidth = 2;
		gbc_checkBoxVenda.insets = new Insets(0, 0, 5, 0);
		gbc_checkBoxVenda.gridx = 1;
		gbc_checkBoxVenda.gridy = 0;
		panelSaidMot.add(checkBoxVenda, gbc_checkBoxVenda);
		
		JLabel lblValorR_1 = new JLabel("Valor:  R$");
		lblValorR_1.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));
		GridBagConstraints gbc_lblValorR_1 = new GridBagConstraints();
		gbc_lblValorR_1.anchor = GridBagConstraints.WEST;
		gbc_lblValorR_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblValorR_1.gridx = 1;
		gbc_lblValorR_1.gridy = 1;
		panelSaidMot.add(lblValorR_1, gbc_lblValorR_1);
		
		estSValorPicker = new JSpinner();
		estSValorPicker.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));
		estSValorPicker.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		estSValorPicker.setEnabled(false);
		GridBagConstraints gbc_estSValorPicker = new GridBagConstraints();
		gbc_estSValorPicker.fill = GridBagConstraints.HORIZONTAL;
		gbc_estSValorPicker.insets = new Insets(0, 0, 5, 0);
		gbc_estSValorPicker.gridx = 2;
		gbc_estSValorPicker.gridy = 1;
		panelSaidMot.add(estSValorPicker, gbc_estSValorPicker);
		
		checkBoxQuebra = new JCheckBox("Quebra");
		checkBoxQuebra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkBoxQuebra.isSelected()){
					checkBoxVenda.setEnabled(false);
					checkBoxSOutro.setEnabled(false);
					estSValorPicker.setEnabled(false);
					estSOutroPicker.setEnabled(false);
				}else{
					checkBoxVenda.setEnabled(true);
					checkBoxSOutro.setEnabled(true);
				} 
			}
		});
		checkBoxQuebra.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));
		GridBagConstraints gbc_checkBoxQuebra = new GridBagConstraints();
		gbc_checkBoxQuebra.gridwidth = 2;
		gbc_checkBoxQuebra.insets = new Insets(0, 0, 5, 0);
		gbc_checkBoxQuebra.gridx = 1;
		gbc_checkBoxQuebra.gridy = 2;
		panelSaidMot.add(checkBoxQuebra, gbc_checkBoxQuebra);
		
		checkBoxSOutro = new JCheckBox("Outros:");
		checkBoxSOutro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkBoxSOutro.isSelected()){
					checkBoxQuebra.setEnabled(false);
					checkBoxVenda.setEnabled(false);
					estSValorPicker.setEnabled(false);
					estSOutroPicker.setEnabled(true);
				}else{
					checkBoxQuebra.setEnabled(true);
					checkBoxVenda.setEnabled(true);
					estSOutroPicker.setEnabled(false);
				}
			}
		});
		checkBoxSOutro.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));
		GridBagConstraints gbc_checkBoxSOutro = new GridBagConstraints();
		gbc_checkBoxSOutro.gridwidth = 2;
		gbc_checkBoxSOutro.insets = new Insets(0, 0, 5, 0);
		gbc_checkBoxSOutro.gridx = 1;
		gbc_checkBoxSOutro.gridy = 3;
		panelSaidMot.add(checkBoxSOutro, gbc_checkBoxSOutro);
		
		estSOutroPicker = new JTextField();
		estSOutroPicker.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));
		estSOutroPicker.setColumns(10);
		estSOutroPicker.setEnabled(false);
		GridBagConstraints gbc_estSOutroPicker = new GridBagConstraints();
		gbc_estSOutroPicker.fill = GridBagConstraints.HORIZONTAL;
		gbc_estSOutroPicker.gridwidth = 2;
		gbc_estSOutroPicker.gridx = 1;
		gbc_estSOutroPicker.gridy = 4;
		panelSaidMot.add(estSOutroPicker, gbc_estSOutroPicker);
		
		JPanel panelEntradaEstoque = new JPanel();
		panelEstOp.add(panelEntradaEstoque, "entradaEstoque");
		GridBagLayout gbl_panelEntradaEstoque = new GridBagLayout();
		gbl_panelEntradaEstoque.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panelEntradaEstoque.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelEntradaEstoque.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panelEntradaEstoque.rowWeights = new double[]{1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		panelEntradaEstoque.setLayout(gbl_panelEntradaEstoque);
		
		JPanel panelEntPrd = new JPanel();
		panelEntPrd.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_panelEntPrd = new GridBagConstraints();
		gbc_panelEntPrd.gridwidth = 3;
		gbc_panelEntPrd.insets = new Insets(0, 0, 5, 0);
		gbc_panelEntPrd.fill = GridBagConstraints.BOTH;
		gbc_panelEntPrd.gridx = 0;
		gbc_panelEntPrd.gridy = 0;
		panelEntradaEstoque.add(panelEntPrd, gbc_panelEntPrd);
		panelEntPrd.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblProduto_1 = new JLabel("Produto:");
		lblProduto_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblProduto_1.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		panelEntPrd.add(lblProduto_1);
		
		estEProdutoPicker = new JComboBox<String>();
		estEProdutoPicker.setMaximumRowCount(3);
		estEProdutoPicker.setModel(new DefaultComboBoxModel<String>(new String[] {"Mesas", "Cadeiras", "Jogos"}));
		estEProdutoPicker.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		panelEntPrd.add(estEProdutoPicker);
		
		JSeparator separator_7 = new JSeparator();
		GridBagConstraints gbc_separator_7 = new GridBagConstraints();
		gbc_separator_7.gridwidth = 3;
		gbc_separator_7.insets = new Insets(0, 0, 5, 5);
		gbc_separator_7.gridx = 0;
		gbc_separator_7.gridy = 1;
		panelEntradaEstoque.add(separator_7, gbc_separator_7);
		
		JPanel panelEntQtd = new JPanel();
		panelEntQtd.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_panelEntQtd = new GridBagConstraints();
		gbc_panelEntQtd.gridwidth = 3;
		gbc_panelEntQtd.insets = new Insets(0, 0, 5, 0);
		gbc_panelEntQtd.fill = GridBagConstraints.BOTH;
		gbc_panelEntQtd.gridx = 0;
		gbc_panelEntQtd.gridy = 2;
		panelEntradaEstoque.add(panelEntQtd, gbc_panelEntQtd);
		panelEntQtd.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel label = new JLabel("Quantidade:");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		panelEntQtd.add(label);
		
		estEQtdPicker = new JSpinner();
		estEQtdPicker.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		estEQtdPicker.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		panelEntQtd.add(estEQtdPicker);
		
		JSeparator separator_4 = new JSeparator();
		GridBagConstraints gbc_separator_4 = new GridBagConstraints();
		gbc_separator_4.gridwidth = 3;
		gbc_separator_4.insets = new Insets(0, 0, 5, 0);
		gbc_separator_4.gridx = 0;
		gbc_separator_4.gridy = 3;
		panelEntradaEstoque.add(separator_4, gbc_separator_4);
		
		JPanel panelEntMot = new JPanel();
		panelEntMot.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_panelEntMot = new GridBagConstraints();
		gbc_panelEntMot.gridwidth = 3;
		gbc_panelEntMot.gridheight = 5;
		gbc_panelEntMot.fill = GridBagConstraints.BOTH;
		gbc_panelEntMot.gridx = 0;
		gbc_panelEntMot.gridy = 5;
		panelEntradaEstoque.add(panelEntMot, gbc_panelEntMot);
		GridBagLayout gbl_panelEntMot = new GridBagLayout();
		gbl_panelEntMot.columnWidths = new int[]{41, 0, 36, 0};
		gbl_panelEntMot.rowHeights = new int[]{14, 0, 0, 0, 0};
		gbl_panelEntMot.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panelEntMot.rowWeights = new double[]{1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		panelEntMot.setLayout(gbl_panelEntMot);
		
		JLabel label_2 = new JLabel("Motivo:");
		label_2.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.fill = GridBagConstraints.VERTICAL;
		gbc_label_2.gridheight = 4;
		gbc_label_2.insets = new Insets(0, 0, 0, 5);
		gbc_label_2.gridx = 0;
		gbc_label_2.gridy = 0;
		panelEntMot.add(label_2, gbc_label_2);
		
		checkBoxCompra = new JCheckBox("Compra");
		checkBoxCompra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkBoxCompra.isSelected()){
					estEValorPicker.setEnabled(true);
					checkBoxEOutro.setEnabled(false);
					estEOutroPicker.setEnabled(false);
				}else{
					estEValorPicker.setEnabled(false);
					checkBoxEOutro.setEnabled(true);
				}
			}
		});
		checkBoxCompra.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		GridBagConstraints gbc_checkBoxCompra = new GridBagConstraints();
		gbc_checkBoxCompra.gridwidth = 2;
		gbc_checkBoxCompra.insets = new Insets(0, 0, 5, 0);
		gbc_checkBoxCompra.gridx = 1;
		gbc_checkBoxCompra.gridy = 0;
		panelEntMot.add(checkBoxCompra, gbc_checkBoxCompra);
		
		JLabel lblValorR = new JLabel("Valor:  R$");
		lblValorR.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		GridBagConstraints gbc_lblValorR = new GridBagConstraints();
		gbc_lblValorR.insets = new Insets(0, 0, 5, 5);
		gbc_lblValorR.anchor = GridBagConstraints.WEST;
		gbc_lblValorR.gridx = 1;
		gbc_lblValorR.gridy = 1;
		panelEntMot.add(lblValorR, gbc_lblValorR);
		
		estEValorPicker = new JSpinner();
		estEValorPicker.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		estEValorPicker.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		estEValorPicker.setEnabled(false);
		GridBagConstraints gbc_estEValorPicker = new GridBagConstraints();
		gbc_estEValorPicker.fill = GridBagConstraints.HORIZONTAL;
		gbc_estEValorPicker.insets = new Insets(0, 0, 5, 0);
		gbc_estEValorPicker.gridx = 2;
		gbc_estEValorPicker.gridy = 1;
		panelEntMot.add(estEValorPicker, gbc_estEValorPicker);
		
		checkBoxEOutro = new JCheckBox("Outros:");
		checkBoxEOutro.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(checkBoxEOutro.isSelected()){
					estEOutroPicker.setEnabled(true);
					checkBoxCompra.setEnabled(false);
					estEValorPicker.setEnabled(false);
				}else{
					estEOutroPicker.setEnabled(false);
					checkBoxCompra.setEnabled(true);
				}
			}
		});
		checkBoxEOutro.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		GridBagConstraints gbc_checkBoxEOutro = new GridBagConstraints();
		gbc_checkBoxEOutro.gridwidth = 2;
		gbc_checkBoxEOutro.insets = new Insets(0, 0, 5, 0);
		gbc_checkBoxEOutro.gridx = 1;
		gbc_checkBoxEOutro.gridy = 2;
		panelEntMot.add(checkBoxEOutro, gbc_checkBoxEOutro);
		
		estEOutroPicker = new JTextField();
		estEOutroPicker.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		estEOutroPicker.setColumns(10);
		estEOutroPicker.setEnabled(false);
		GridBagConstraints gbc_estEOutroPicker = new GridBagConstraints();
		gbc_estEOutroPicker.gridwidth = 2;
		gbc_estEOutroPicker.fill = GridBagConstraints.HORIZONTAL;
		gbc_estEOutroPicker.gridx = 1;
		gbc_estEOutroPicker.gridy = 3;
		panelEntMot.add(estEOutroPicker, gbc_estEOutroPicker);
		
		JPanel panelInfo = new JPanel();
		panelInfo.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelWorkstationComAviso.add(panelInfo, BorderLayout.SOUTH);
		panelInfo.setLayout(new BorderLayout(0, 0));
		
		JLabel lblInformaesELembretes = new JLabel("    Informa\u00E7\u00F5es e Lembretes: ");
		panelInfo.add(lblInformaesELembretes, BorderLayout.NORTH);
		txtrInformaesAqui.setEditable(false);
		
		txtrInformaesAqui.setForeground(Color.BLUE);
		txtrInformaesAqui.setFont(new Font("Monospaced", Font.PLAIN, 12));
		txtrInformaesAqui.setText("Informa\u00E7\u00F5es aqui...");
		txtrInformaesAqui.setToolTipText("Informa\u00E7\u00F5es");
		panelInfo.add(txtrInformaesAqui, BorderLayout.CENTER);
	}
	
	ActionListener cliPanListener = new ActionListener(){
		public void actionPerformed(ActionEvent event){
			subTitulo.setText("Gestão de Clientes");
			layoutController.show(panelWorkstation, "clientePanel");
			gestaoClienteBtn.setEnabled(false);
			gestaoEstoqueBtn.setEnabled(true);
			btnCliAnterior.setEnabled(false);
			btnAdicionarCliente.setEnabled(true);
			btnRemoverCliente.setEnabled(true);
			try {
				updateTableClientes();
			}catch (SQLException e){
				e.printStackTrace();
			}
		}
	};
	
	ActionListener estPanListener = new ActionListener(){
		public void actionPerformed(ActionEvent event){
			subTitulo.setText("Gestão de Estoque");
			btnEstAnterior.setEnabled(false);
			btnEstSalvar.setEnabled(false);
			gestaoEstoqueBtn.setEnabled(false);
			gestaoClienteBtn.setEnabled(true);
			panelEstOpLayout.first(panelEstOp);
			layoutController.show(panelWorkstation, "estoquePanel");
			estEOutroPicker.setText("");
			estEOutroPicker.setEnabled(false);
			estSOutroPicker.setText("");
			estSOutroPicker.setEnabled(false);
			checkBoxSOutro.setSelected(false);
			checkBoxEOutro.setSelected(false);
		}
	};
	
	ActionListener homPanListener = new ActionListener(){
		public void actionPerformed(ActionEvent event){
			try {
				updateWorkstation();
			}catch (SQLException e){
				e.printStackTrace();
			}
			gestaoEstoqueBtn.setEnabled(true);
			gestaoClienteBtn.setEnabled(true);
			subTitulo.setText("Informa\u00E7\u00F5es de Estoque");
			layoutController.show(panelWorkstation, "homePanel");
		}
	};
	
	ActionListener estExecListener = new ActionListener(){
		public void actionPerformed(ActionEvent event){
		String aviso="";
			try(Connection con = new ConnectionPool().getConnection()){
				if(isEntrada){
					if((Integer)estEQtdPicker.getValue()>0){
						switch((String) estEProdutoPicker.getSelectedItem()){
							case "Mesas":
								diaAluguel.adicionaMesas((Integer) estEQtdPicker.getValue(), con);
								aviso ="Estoque atualizado com mais "+(Integer) estEQtdPicker.getValue()+" mesas!";
								break;
							case "Cadeiras":
								diaAluguel.adicionaCadeiras((Integer) estEQtdPicker.getValue(), con);
								aviso ="Estoque atualizado com mais "+(Integer) estEQtdPicker.getValue()+" mesas!";
								break;
							case "Jogos":
								diaAluguel.adicionaMesas((Integer) estEQtdPicker.getValue(),con);
								diaAluguel.adicionaCadeiras(((Integer) estEQtdPicker.getValue())*4, con);
								aviso ="Estoque atualizado com mais "+(Integer) estEQtdPicker.getValue()+" jogos!";
								break;
						}
					}else{
						aviso="Quantidade inválida! Estoque não foi atualizado!";
					}
				}else if((Integer)estSQtdPicker.getValue()>0){
					switch((String) estSProdutoPicker.getSelectedItem()){
						case "Mesas":
							diaAluguel.removeMesas((Integer) estSQtdPicker.getValue(), con);
							aviso ="Estoque atualizado com menos "+(Integer) estSQtdPicker.getValue()+" mesas!";
							break;
						case "Cadeiras":
							diaAluguel.removeCadeiras((Integer) estSQtdPicker.getValue(), con);
							aviso ="Estoque atualizado com menos "+(Integer) estSQtdPicker.getValue()+" cadeiras!";
							break;
						case "Jogos":
							diaAluguel.removeMesas((Integer) estSQtdPicker.getValue(), con);
							diaAluguel.removeCadeiras(((Integer) estSQtdPicker.getValue())*4, con);
							aviso ="Estoque atualizado com menos "+(Integer) estSQtdPicker.getValue()+" jogos!";
							break;
					}
				} else {
					aviso="Quantidade inválida! Estoque não foi atualizado!";
				}
			}catch (SQLException e){
				e.printStackTrace();
				aviso = "Não foi possível salvar devido a um ERRO SQL";
			}finally{
				try {
					updateWorkstation(aviso);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			gestaoEstoqueBtn.setEnabled(true);
			gestaoClienteBtn.setEnabled(true);
			subTitulo.setText("Informa\u00E7\u00F5es de Estoque");
			layoutController.show(panelWorkstation, "homePanel");
		}
	};
	
	ActionListener estAnteriorListener = new ActionListener(){
		public void actionPerformed(ActionEvent event){
			btnEstAnterior.setEnabled(false);
			btnEstSalvar.setEnabled(false);
			panelEstOpLayout.first(panelEstOp);
			subTitulo.setText("Gestão de Estoque");
		}
	};
	
	ActionListener btnOp1EntradaListener = new ActionListener(){
		public void actionPerformed(ActionEvent event){
			panelEstOpLayout.show(panelEstOp, "entradaEstoque");
			btnEstAnterior.setEnabled(true);
			btnEstSalvar.setEnabled(true);
			isEntrada = true;
			subTitulo.setText("Entrada em Estoque");
			updateEstoqueView();
		}
	};
	
	ActionListener btnOp1SaidaListener = new ActionListener(){
		public void actionPerformed(ActionEvent event){
			panelEstOpLayout.show(panelEstOp, "saidaEstoque");
			btnEstAnterior.setEnabled(true);
			btnEstSalvar.setEnabled(true);
			isEntrada = false;
			subTitulo.setText("Saída em Estoque");
			updateEstoqueView();
		}
	};
	
	ActionListener btnAdicionarClienteListener = new ActionListener(){
		public void actionPerformed(ActionEvent event){
			CardLayout layout = (CardLayout) panelClientCard.getLayout();
			layout.show(panelClientCard, "panAddCli");
			btnCliAnterior.setEnabled(true);
			btnAdicionarCliente.setEnabled(false);
			btnRemoverCliente.setEnabled(true);
		}
	};
	
	ActionListener btnRemoverClienteListener = new ActionListener(){
		public void actionPerformed(ActionEvent event){
			nomeClienteRemovido.setText((String) tableClientes.getValueAt(tableClientes.getSelectedRow(), 1));
			CardLayout layout = (CardLayout) panelClientCard.getLayout();
			layout.show(panelClientCard, "panRemCli");
			btnCliAnterior.setEnabled(true);
			btnAdicionarCliente.setEnabled(true);
			btnRemoverCliente.setEnabled(false);
		}
	};
	
	ActionListener btnRemoverSimListener = new ActionListener() {
		public void actionPerformed(ActionEvent event){
			int row = tableClientes.getSelectedRow();
			int id = Integer.parseInt((String) tableClientes.getValueAt(row, 0));
			try{
				clientesDAO.removeCliente(id);
				updateTableClientes();
			}catch(SQLException e){
				e.printStackTrace();
			}
			CardLayout layout = (CardLayout) panelClientCard.getLayout();
			layout.show(panelClientCard, "panCli");
			btnCliAnterior.setEnabled(false);
			btnAdicionarCliente.setEnabled(true);
			btnRemoverCliente.setEnabled(true);
		}
	};
	
	ActionListener btnCliAnteriorListener = new ActionListener(){
		public void actionPerformed(ActionEvent event){
			CardLayout layout = (CardLayout) panelClientCard.getLayout();
			layout.show(panelClientCard, "panCli");
			btnCliAnterior.setEnabled(false);
			btnAdicionarCliente.setEnabled(true);
			btnRemoverCliente.setEnabled(true);
		}
	};
	
	ActionListener btnCadastrarClienteListener = new ActionListener(){
		public void actionPerformed(ActionEvent event){
			try{
				clientesDAO.adicionaCliente(new Cliente(txtNome.getText(), txtEnd.getText(), txtTel.getText()));
			}catch(SQLException e){
				e.printStackTrace();
			}
			CardLayout layout = (CardLayout) panelClientCard.getLayout();
			layout.show(panelClientCard, "panCli");
			btnCliAnterior.setEnabled(false);
			btnAdicionarCliente.setEnabled(true);
			btnRemoverCliente.setEnabled(true);
			txtNome.setText("");
			txtEnd.setText("");
			txtTel.setText("");
			try{
				updateTableClientes();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	};

	
	private void updateWorkstation() throws SQLException{
		jogDispLbl.setText(Integer.toString(diaAluguel.getEstoqueJogos().getJogosDisponiveis()));
		mesDispLbl.setText(Integer.toString(diaAluguel.getEstoqueJogos().getEstoqueMesas().getMesasDisponiveis()));
		cadDispLbl.setText(Integer.toString(diaAluguel.getEstoqueJogos().getEstoqueCadeiras().getCadeirasDisponiveis()));
		totCadLbl.setText(Integer.toString(diaAluguel.getEstoqueJogos().getEstoqueCadeiras().getTotalCadeiras()));
		totMesLbl.setText(Integer.toString(diaAluguel.getEstoqueJogos().getEstoqueMesas().getTotalMesas()));
		totJogLbl.setText(Integer.toString(diaAluguel.getEstoqueJogos().getTotalJogos()));
		totCliLbl.setText(Integer.toString(clientesDAO.getQtdClientes()));
		txtrInformaesAqui.setText("Informa\u00E7\u00F5es aqui...");
		txtrInformaesAqui.setForeground(Color.BLUE);
	}
	
	private void updateWorkstation(String feedback) throws SQLException{
		updateWorkstation();
		txtrInformaesAqui.setText(feedback+" ["+new GregorianCalendar().getTime()+"]");
		txtrInformaesAqui.setForeground(Color.RED);
	}
	
	private void updateTableClientes() throws SQLException{
		Cliente cliente;
		String[] colunas = {"Id","Nome","Endereço","Telefone"};
		String[][] dados = new String[clientesDAO.getQtdClientes()][4];
		for (int i=0; i<clientesDAO.getQtdClientes(); i++) {
			cliente = clientesDAO.getListaClientes().get(i);
			dados[i][0] = Integer.toString(cliente.getId());
			dados[i][1] = cliente.getNome();
			dados[i][2] = cliente.getEnd();
			dados[i][3] = cliente.getTel();
		}
		tableClientes = new JTable(dados, colunas);
		JScrollPane tabCliScroll = new JScrollPane(tableClientes);
		panelListaCliente.removeAll();
		panelListaCliente.add(tabCliScroll, BorderLayout.CENTER);
		panelListaCliente.add(tableClientes.getTableHeader(), BorderLayout.NORTH);
	}

	private void updateEstoqueView() {
		estEOutroPicker.setText("");
		estSOutroPicker.setText("");
		estEQtdPicker.setValue(new Integer(0));
		estSQtdPicker.setValue(new Integer(0));
		estEValorPicker.setValue(new Integer(0));
		estSValorPicker.setValue(new Integer(0));
		checkBoxSOutro.setSelected(false);
		checkBoxEOutro.setSelected(false);
		checkBoxQuebra.setSelected(false);
		checkBoxVenda.setSelected(false);
		checkBoxCompra.setSelected(false);
		estEOutroPicker.setEnabled(false);
		estSOutroPicker.setEnabled(false);
		estEValorPicker.setEnabled(false);
		estSValorPicker.setEnabled(false);
		checkBoxSOutro.setEnabled(true);
		checkBoxEOutro.setEnabled(true);
		checkBoxQuebra.setEnabled(true);
		checkBoxVenda.setEnabled(true);
		checkBoxCompra.setEnabled(true);
	}
}
