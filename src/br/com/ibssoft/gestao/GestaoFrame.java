package br.com.ibssoft.gestao;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuItem;

public class GestaoFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private boolean isEntrada;
	
	private JPanel contentPane;
	private CardLayout layoutController = new CardLayout(0,0);
	private CardLayout panelEstOpLayout;
	private JTextField estEOutroPicker;
	private JTextField estSOutroPicker;
	private JPanel panelWorkstation;
	private JPanel homePanel;
	private JPanel estoquePanel;
	private JPanel clientePanel;
	private JPanel panelEstOp;
	private JLabel subTitulo;
	private JLabel totJogLbl = new JLabel("0");
	private JLabel totCadLbl = new JLabel("0");
	private JLabel totMesLbl = new JLabel("0");
	private JLabel mesDispLbl = new JLabel("0");
	private JLabel cadDispLbl = new JLabel("0");
	private JLabel jogDispLbl = new JLabel("0");
	private Gestao gestao = new Gestao();
	private JButton btnEstInicio;
	private JButton btnEstAnterior;
	private JButton btnEstSalvar;
	private JButton btnOp1Entrada;
	private JButton btnOp1Saida;
	private JButton gestaoEstoqueBtn;
	private JButton gestaoClienteBtn;
	private JComboBox estSProdutoPicker;
	private JComboBox estEProdutoPicker; 
	private JSpinner estSQtdPicker;
	private JSpinner estSValorPicker;
	private JSpinner estEQtdPicker;
	private JSpinner estEValorPicker;
	private JCheckBox checkBoxVenda;
	private JCheckBox checkBoxQuebra;
	private JCheckBox checkBoxEOutro;
	private JCheckBox checkBoxSOutro;
	private JCheckBox checkBoxCompra;
	private JMenuItem mntmSalvar;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gestao g = new Gestao();
					g.startOp();
					GestaoFrame frame = new GestaoFrame(g);
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
	public GestaoFrame(Gestao gest) {
		gestao = gest;
		gest=null;
		
		setTitle("Gest\u00E3o Mesas e Cadeiras");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnArquivo = new JMenu("Arquivo");
		menuBar.add(mnArquivo);
		
		mntmSalvar = new JMenuItem("Salvar");
		mntmSalvar.addActionListener(mntmSalvarListener);
		mnArquivo.add(mntmSalvar);
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
		
		subTitulo = new JLabel("Informa\u00E7\u00F5es de Estoque");
		panelSubTitulo.add(subTitulo);
		subTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		subTitulo.setForeground(Color.BLUE);
		subTitulo.setFont(new Font("Microsoft YaHei Light", Font.BOLD, 25));
		
		panelWorkstation = new JPanel();
		panelWorkstation.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelMain.add(panelWorkstation, BorderLayout.CENTER);
		panelWorkstation.setLayout(layoutController);
		
		updateWorkstation(); //Atualiza os valores dos JLabels
		
		homePanel = new JPanel();
		GridBagLayout gbl_homePanel = new GridBagLayout();
		gbl_homePanel.columnWidths = new int[]{187, 0, 0, 0, 0, 0, 0};
		gbl_homePanel.rowHeights = new int[]{29, 29, 29, 29, 29, 29, 0};
		gbl_homePanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_homePanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		homePanel.setLayout(gbl_homePanel);
		
		JLabel label_3 = new JLabel("Total de Cadeiras:");
		label_3.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 22));
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.gridheight = 2;
		gbc_label_3.anchor = GridBagConstraints.WEST;
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
		gbc_cadDispLbl.insets = new Insets(0, 0, 5, 0);
		gbc_cadDispLbl.gridx = 5;
		gbc_cadDispLbl.gridy = 0;
		homePanel.add(cadDispLbl, gbc_cadDispLbl);
		cadDispLbl.setForeground(new Color(70, 130, 180));
		cadDispLbl.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 22));
		cadDispLbl.setBackground(new Color(248, 248, 255));
		
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
		
		JLabel label_6 = new JLabel("Jogos Dispon\u00EDveis:");
		GridBagConstraints gbc_label_6 = new GridBagConstraints();
		gbc_label_6.gridheight = 2;
		gbc_label_6.insets = new Insets(0, 0, 5, 5);
		gbc_label_6.gridx = 4;
		gbc_label_6.gridy = 2;
		homePanel.add(label_6, gbc_label_6);
		label_6.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 22));
		
		GridBagConstraints gbc_jogDispLbl = new GridBagConstraints();
		gbc_jogDispLbl.gridheight = 2;
		gbc_jogDispLbl.insets = new Insets(0, 0, 5, 0);
		gbc_jogDispLbl.gridx = 5;
		gbc_jogDispLbl.gridy = 2;
		homePanel.add(jogDispLbl, gbc_jogDispLbl);
		jogDispLbl.setForeground(new Color(70, 130, 180));
		jogDispLbl.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 22));
		jogDispLbl.setBackground(new Color(248, 248, 255));
		
		JLabel label_11 = new JLabel("Total de Jogos:");
		label_11.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 22));
		GridBagConstraints gbc_label_11 = new GridBagConstraints();
		gbc_label_11.gridheight = 2;
		gbc_label_11.insets = new Insets(0, 0, 0, 5);
		gbc_label_11.gridx = 0;
		gbc_label_11.gridy = 4;
		homePanel.add(label_11, gbc_label_11);
		panelWorkstation.add(homePanel, "homePanel");
		
		totJogLbl.setForeground(new Color(70, 130, 180));
		totJogLbl.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 22));
		totJogLbl.setBackground(new Color(248, 248, 255));
		GridBagConstraints gbc_totJogLbl = new GridBagConstraints();
		gbc_totJogLbl.gridheight = 2;
		gbc_totJogLbl.insets = new Insets(0, 0, 0, 5);
		gbc_totJogLbl.gridx = 1;
		gbc_totJogLbl.gridy = 4;
		homePanel.add(totJogLbl, gbc_totJogLbl);
		
		JLabel label_4 = new JLabel("Mesas Dispon\u00EDveis:");
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.gridheight = 2;
		gbc_label_4.insets = new Insets(0, 0, 5, 5);
		gbc_label_4.gridx = 4;
		gbc_label_4.gridy = 4;
		homePanel.add(label_4, gbc_label_4);
		label_4.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 22));
		
		GridBagConstraints gbc_mesDispLbl = new GridBagConstraints();
		gbc_mesDispLbl.gridheight = 2;
		gbc_mesDispLbl.insets = new Insets(0, 0, 5, 0);
		gbc_mesDispLbl.gridx = 5;
		gbc_mesDispLbl.gridy = 4;
		homePanel.add(mesDispLbl, gbc_mesDispLbl);
		mesDispLbl.setForeground(new Color(70, 130, 180));
		mesDispLbl.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 22));
		mesDispLbl.setBackground(new Color(248, 248, 255));
		
		clientePanel = new JPanel();
		panelWorkstation.add(clientePanel, "clientePanel");
		
		JButton voltarCBtn = new JButton("Voltar C");
		voltarCBtn.addActionListener(homPanListener);
		clientePanel.add(voltarCBtn);
		
		estoquePanel = new JPanel();
		panelWorkstation.add(estoquePanel, "estoquePanel");
		GridBagLayout gbl_estoquePanel = new GridBagLayout();
		gbl_estoquePanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_estoquePanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_estoquePanel.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_estoquePanel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		estoquePanel.setLayout(gbl_estoquePanel);
		
		JPanel panelEstMenu = new JPanel();
		panelEstMenu.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_panelEstMenu = new GridBagConstraints();
		gbc_panelEstMenu.gridheight = 2;
		gbc_panelEstMenu.insets = new Insets(0, 0, 5, 5);
		gbc_panelEstMenu.fill = GridBagConstraints.BOTH;
		gbc_panelEstMenu.gridx = 0;
		gbc_panelEstMenu.gridy = 0;
		estoquePanel.add(panelEstMenu, gbc_panelEstMenu);
		panelEstMenu.setLayout(new GridLayout(0, 1, 0, 0));
		
		btnEstInicio = new JButton("Voltar");
		btnEstInicio.addActionListener(homPanListener);
		btnEstInicio.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		panelEstMenu.add(btnEstInicio);
		
		btnEstAnterior = new JButton("Anterior");
		btnEstAnterior.addActionListener(estAnteriorListener);
		btnEstAnterior.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		panelEstMenu.add(btnEstAnterior);
		
		btnEstSalvar = new JButton("Salvar");
		btnEstSalvar.addActionListener(estExecListener);
		btnEstSalvar.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		panelEstMenu.add(btnEstSalvar);
		
		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.gridheight = 2;
		gbc_separator.insets = new Insets(0, 0, 5, 5);
		gbc_separator.gridx = 1;
		gbc_separator.gridy = 0;
		estoquePanel.add(separator, gbc_separator);
		
		panelEstOp = new JPanel();
		panelEstOp.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_panelEstOp = new GridBagConstraints();
		gbc_panelEstOp.gridheight = 2;
		gbc_panelEstOp.insets = new Insets(0, 0, 5, 0);
		gbc_panelEstOp.fill = GridBagConstraints.BOTH;
		gbc_panelEstOp.gridx = 2;
		gbc_panelEstOp.gridy = 0;
		estoquePanel.add(panelEstOp, gbc_panelEstOp);
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
		
		estSProdutoPicker = new JComboBox();
		estSProdutoPicker.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));
		estSProdutoPicker.setToolTipText("Escolha uma unidade");
		estSProdutoPicker.setModel(new DefaultComboBoxModel(new String[] {"Mesas", "Cadeiras", "Jogos"}));
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
		estSQtdPicker.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
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
		gbl_panelSaidMot.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
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
		gbc_lblValorR_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblValorR_1.gridx = 1;
		gbc_lblValorR_1.gridy = 1;
		panelSaidMot.add(lblValorR_1, gbc_lblValorR_1);
		
		estSValorPicker = new JSpinner();
		estSValorPicker.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));
		estSValorPicker.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		GridBagConstraints gbc_estSValorPicker = new GridBagConstraints();
		gbc_estSValorPicker.fill = GridBagConstraints.HORIZONTAL;
		gbc_estSValorPicker.insets = new Insets(0, 0, 5, 0);
		gbc_estSValorPicker.gridx = 2;
		gbc_estSValorPicker.gridy = 1;
		panelSaidMot.add(estSValorPicker, gbc_estSValorPicker);
		
		checkBoxQuebra = new JCheckBox("Quebra");
		checkBoxQuebra.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));
		GridBagConstraints gbc_checkBoxQuebra = new GridBagConstraints();
		gbc_checkBoxQuebra.gridwidth = 2;
		gbc_checkBoxQuebra.insets = new Insets(0, 0, 5, 0);
		gbc_checkBoxQuebra.gridx = 1;
		gbc_checkBoxQuebra.gridy = 2;
		panelSaidMot.add(checkBoxQuebra, gbc_checkBoxQuebra);
		
		checkBoxSOutro = new JCheckBox("Outros:");
		checkBoxSOutro.addActionListener(checkBoxSOutroListener);
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
		
		estEProdutoPicker = new JComboBox();
		estEProdutoPicker.setMaximumRowCount(3);
		estEProdutoPicker.setModel(new DefaultComboBoxModel(new String[] {"Mesas", "Cadeiras", "Jogos"}));
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
		estEQtdPicker.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
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
		gbl_panelEntMot.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
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
		gbc_lblValorR.anchor = GridBagConstraints.EAST;
		gbc_lblValorR.gridx = 1;
		gbc_lblValorR.gridy = 1;
		panelEntMot.add(lblValorR, gbc_lblValorR);
		
		estEValorPicker = new JSpinner();
		estEValorPicker.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		estEValorPicker.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		GridBagConstraints gbc_estEValorPicker = new GridBagConstraints();
		gbc_estEValorPicker.fill = GridBagConstraints.HORIZONTAL;
		gbc_estEValorPicker.insets = new Insets(0, 0, 5, 0);
		gbc_estEValorPicker.gridx = 2;
		gbc_estEValorPicker.gridy = 1;
		panelEntMot.add(estEValorPicker, gbc_estEValorPicker);
		
		checkBoxEOutro = new JCheckBox("Outros:");
		checkBoxEOutro.addActionListener(checkBoxEOutroListener);
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
		GridBagConstraints gbc_estEOutroPicker = new GridBagConstraints();
		gbc_estEOutroPicker.gridwidth = 2;
		gbc_estEOutroPicker.fill = GridBagConstraints.HORIZONTAL;
		gbc_estEOutroPicker.gridx = 1;
		gbc_estEOutroPicker.gridy = 3;
		panelEntMot.add(estEOutroPicker, gbc_estEOutroPicker);
		
		JSeparator separator_2 = new JSeparator();
		GridBagConstraints gbc_separator_2 = new GridBagConstraints();
		gbc_separator_2.gridwidth = 3;
		gbc_separator_2.insets = new Insets(0, 0, 5, 0);
		gbc_separator_2.gridx = 0;
		gbc_separator_2.gridy = 2;
		estoquePanel.add(separator_2, gbc_separator_2);
		
		JPanel panelInfo = new JPanel();
		panelInfo.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_panelInfo = new GridBagConstraints();
		gbc_panelInfo.gridwidth = 3;
		gbc_panelInfo.fill = GridBagConstraints.BOTH;
		gbc_panelInfo.gridx = 0;
		gbc_panelInfo.gridy = 3;
		estoquePanel.add(panelInfo, gbc_panelInfo);
		panelInfo.setLayout(new BorderLayout(0, 0));
		
		JLabel lblInformaesELembretes = new JLabel("    Informa\u00E7\u00F5es e Lembretes: ");
		panelInfo.add(lblInformaesELembretes, BorderLayout.NORTH);
		
		JTextArea txtrInformaesAqui = new JTextArea();
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
		}
	};
	
	ActionListener estPanListener = new ActionListener(){
		public void actionPerformed(ActionEvent event){
			subTitulo.setText("Gestão de Estoque");
			btnEstAnterior.setEnabled(false);
			btnEstSalvar.setEnabled(false);
			gestaoEstoqueBtn.setEnabled(false);
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
			updateWorkstation();
			gestaoEstoqueBtn.setEnabled(true);
			gestaoClienteBtn.setEnabled(true);
			subTitulo.setText("Informa\u00E7\u00F5es de Estoque");
			layoutController.show(panelWorkstation, "homePanel");
		}
	};
	
	ActionListener estExecListener = new ActionListener(){
		public void actionPerformed(ActionEvent event){
			if(isEntrada){
				switch((String) estEProdutoPicker.getSelectedItem()){
					case "Mesas":
						gestao.getEstMes().adicionaMesas((Integer) estEQtdPicker.getValue());
						break;
					case "Cadeiras":
						gestao.getEstCad().adicionaCadeiras((Integer) estEQtdPicker.getValue()); 
						break;
					case "Jogos":
						gestao.getEstMes().adicionaMesas((Integer) estEQtdPicker.getValue());
						gestao.getEstCad().adicionaCadeiras(((Integer) estEQtdPicker.getValue())*4);
						break;
				}
			}else{
				switch((String) estSProdutoPicker.getSelectedItem()){
					case "Mesas":
						gestao.getEstMes().removeMesas((Integer) estSQtdPicker.getValue());
						break;
					case "Cadeiras":
						gestao.getEstCad().removeCadeiras((Integer) estSQtdPicker.getValue()); 
						break;
					case "Jogos":
						gestao.getEstMes().removeMesas((Integer) estSQtdPicker.getValue());
						gestao.getEstCad().removeCadeiras(((Integer) estSQtdPicker.getValue())*4);
						break;
				}
			}
			updateWorkstation();
			gestaoEstoqueBtn.setEnabled(true);
			gestaoClienteBtn.setEnabled(true);
			subTitulo.setText("Informa\u00E7\u00F5es de Estoque");
			layoutController.show(panelWorkstation, "homePanel");
			estEOutroPicker.setText("");
			estEOutroPicker.setEnabled(false);
			estSOutroPicker.setText("");
			estSOutroPicker.setEnabled(false);
			checkBoxSOutro.setSelected(false);
			checkBoxEOutro.setSelected(false);
		}
	};
	
	ActionListener estAnteriorListener = new ActionListener(){
		public void actionPerformed(ActionEvent event){
			btnEstAnterior.setEnabled(false);
			btnEstSalvar.setEnabled(false);
			panelEstOpLayout.first(panelEstOp);
			subTitulo.setText("Gestão de Estoque");
			estEOutroPicker.setText("");
			estEOutroPicker.setEnabled(false);
			estSOutroPicker.setText("");
			estSOutroPicker.setEnabled(false);
			checkBoxSOutro.setSelected(false);
			checkBoxEOutro.setSelected(false);
		}
	};
	
	ActionListener btnOp1EntradaListener = new ActionListener(){
		public void actionPerformed(ActionEvent event){
			panelEstOpLayout.show(panelEstOp, "entradaEstoque");
			btnEstAnterior.setEnabled(true);
			btnEstSalvar.setEnabled(true);
			isEntrada = true;
			subTitulo.setText("Entrada em Estoque");
			estEOutroPicker.setEnabled(false);
		}
	};
	
	ActionListener btnOp1SaidaListener = new ActionListener(){
		public void actionPerformed(ActionEvent event){
			panelEstOpLayout.show(panelEstOp, "saidaEstoque");
			btnEstAnterior.setEnabled(true);
			btnEstSalvar.setEnabled(true);
			isEntrada = false;
			subTitulo.setText("Saída em Estoque");
			estSOutroPicker.setEnabled(false);
		}
	};
	
	ActionListener checkBoxEOutroListener = new ActionListener(){
		public void actionPerformed(ActionEvent event){
			estEOutroPicker.setEnabled(checkBoxEOutro.isSelected());
		}
	};
	
	ActionListener checkBoxSOutroListener = new ActionListener(){
		public void actionPerformed(ActionEvent event){
			estSOutroPicker.setEnabled(checkBoxSOutro.isSelected());
		}
	};
	
	ActionListener mntmSalvarListener = new ActionListener(){
		public void actionPerformed(ActionEvent event){
			gestao.stopOp();
		}
	};
	
	private void updateWorkstation(){
		jogDispLbl.setText(Integer.toString(gestao.getEstJog().getJogosDisponiveis()));
		mesDispLbl.setText(Integer.toString(gestao.getEstMes().getMesasDisponiveis()));
		cadDispLbl.setText(Integer.toString(gestao.getEstCad().getCadeirasDisponiveis()));
		totCadLbl.setText(Integer.toString(gestao.getEstCad().getTotalCadeiras()));
		totMesLbl.setText(Integer.toString(gestao.getEstMes().getTotalMesas()));
		totJogLbl.setText(Integer.toString(gestao.getEstJog().getTotalJogos()));
	}
}
