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

public class GestaoFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private CardLayout layoutController = new CardLayout(0,0);
	private JPanel panelWorkstation;
	private JPanel homePanel;
	private JPanel estoquePanel;
	private JPanel clientePanel;
	private JLabel totJogLbl = new JLabel("0");
	private JLabel totCadLbl = new JLabel("0");
	private JLabel totMesLbl = new JLabel("0");
	private JLabel mesDispLbl = new JLabel("0");
	private JLabel cadDispLbl = new JLabel("0");
	private JLabel jogDispLbl = new JLabel("0");
	private Gestao gestao = new Gestao();

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
		contentPane = new JPanel();
		contentPane.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelTitulo = new JPanel();
		panelTitulo.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		contentPane.add(panelTitulo, BorderLayout.NORTH);
		
		JLabel label_13 = new JLabel("Gest\u00E3o Mesas e Cadeiras");
		label_13.setHorizontalAlignment(SwingConstants.CENTER);
		label_13.setFont(new Font("Microsoft YaHei Light", Font.BOLD, 18));
		label_13.setBackground(Color.WHITE);
		panelTitulo.add(label_13);
		
		JPanel panelBtnsAcesso = new JPanel();
		panelBtnsAcesso.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		contentPane.add(panelBtnsAcesso, BorderLayout.SOUTH);
		
		JButton gestaoEstoqueBtn = new JButton("Gest\u00E3o Estoque");
		gestaoEstoqueBtn.addActionListener(estPanListener);
		gestaoEstoqueBtn.setFont(new Font("Microsoft YaHei Light", Font.PLAIN, 30));
		panelBtnsAcesso.setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton gestaoClienteBtn = new JButton("Gest\u00E3o Cliente");
		gestaoClienteBtn.addActionListener(cliPanListener);
		gestaoClienteBtn.setFont(new Font("Microsoft YaHei Light", Font.PLAIN, 30));
		panelBtnsAcesso.add(gestaoClienteBtn);
		panelBtnsAcesso.add(gestaoEstoqueBtn);
		
		JPanel panelMain = new JPanel();
		panelMain.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		contentPane.add(panelMain, BorderLayout.CENTER);
		panelMain.setLayout(new BorderLayout(0, 0));
		
		JPanel panelSubTitulo = new JPanel();
		panelMain.add(panelSubTitulo, BorderLayout.NORTH);
		
		JLabel subTitulo = new JLabel("Informa\u00E7\u00F5es de Estoque");
		panelSubTitulo.add(subTitulo);
		subTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		subTitulo.setForeground(Color.BLUE);
		subTitulo.setFont(new Font("Microsoft YaHei Light", Font.BOLD, 25));
		
		panelWorkstation = new JPanel();
		panelWorkstation.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
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
		
		JButton voltarEBtn = new JButton("Voltar E");
		voltarEBtn.addActionListener(homPanListener);
		estoquePanel.add(voltarEBtn);
	}
	
	ActionListener cliPanListener = new ActionListener(){
		public void actionPerformed(ActionEvent event){
			layoutController.show(panelWorkstation, "clientePanel");
		}
	};
	
	ActionListener estPanListener = new ActionListener(){
		public void actionPerformed(ActionEvent event){
			layoutController.show(panelWorkstation, "estoquePanel");
		}
	};
	
	ActionListener homPanListener = new ActionListener(){
		public void actionPerformed(ActionEvent event){
			updateWorkstation();
			layoutController.show(panelWorkstation, "homePanel");
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
