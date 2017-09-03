
package br.com.ibssoft.gestao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;

import br.com.ibssoft.database.AlugueisDAO;
import br.com.ibssoft.database.ConnectionPool;
import br.com.ibssoft.gestao.aluguel.DiaAluguel;
import br.com.ibssoft.gestao.estoque.EstoqueJogos;
import javax.swing.table.DefaultTableModel;

public class AluguelFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableAlugueis;
	private JPanel panelTable;
	private JPanel panelTableTitle;
	private JPanel panelInfo;
	private JLabel lblTableTitle;
	private JLabel lblQtdCadDisp;
	private JLabel lblQtdMesDisp;
	private JLabel lblQtdJogDisp;
	private JLabel lblQtdTotCad;
	private JLabel lblQtdTotMes;
	private JLabel lblQtdTotJog;
	private JProgressBar progressBarCadDisp;
	private JProgressBar progressBarMesDisp;
	private JProgressBar progressBarJogDisp;
	
	private JFrame thisFrame;
	private Connection connection;
	private AlugueisDAO dao;
	private EstoqueJogos estoque;
	
	/**
	 * Launch the application
	 */
	public static void main(String[] args) {
		LocalDate data = (LocalDate.now());
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AluguelFrame frame = new AluguelFrame(data);
					//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);  // Maximiza ao iniciar
					frame.setVisible(true);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	@SuppressWarnings("serial")
	public AluguelFrame(LocalDate data) throws SQLException {
		connection = new ConnectionPool().getConnection();
		dao = new AlugueisDAO(connection);
		thisFrame = this;
		
		setTitle("Aluguel Mesas & Cadeiras");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelBtns = new JPanel();
		panelBtns.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		contentPane.add(panelBtns, BorderLayout.EAST);
		GridBagLayout gbl_panelBtns = new GridBagLayout();
		gbl_panelBtns.columnWidths = new int[]{0, 0};
		gbl_panelBtns.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panelBtns.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panelBtns.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0};
		panelBtns.setLayout(gbl_panelBtns);
		
		JButton btnNovoAluguel = new JButton("Novo Aluguel");
		btnNovoAluguel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		btnNovoAluguel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO
			}
		});
		GridBagConstraints gbc_btnNovoAluguel = new GridBagConstraints();
		gbc_btnNovoAluguel.anchor = GridBagConstraints.NORTH;
		gbc_btnNovoAluguel.insets = new Insets(1, 1, 1, 1);
		gbc_btnNovoAluguel.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNovoAluguel.gridx = 0;
		gbc_btnNovoAluguel.gridy = 0;
		panelBtns.add(btnNovoAluguel, gbc_btnNovoAluguel);
		
		JButton btnCancelarAluguel = new JButton("Cancelar Aluguel");
		btnCancelarAluguel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		btnCancelarAluguel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO
				int idAluguelRemovido = Integer.parseInt((String) tableAlugueis.getValueAt(tableAlugueis.getSelectedRow(), 0));
				try {
					dao.removeAluguel(idAluguelRemovido);
					updateTableAlugueis(data);
					updatePanelInfo(data);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnCancelarAluguel = new GridBagConstraints();
		gbc_btnCancelarAluguel.anchor = GridBagConstraints.NORTH;
		gbc_btnCancelarAluguel.insets = new Insets(1, 1, 1, 1);
		gbc_btnCancelarAluguel.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCancelarAluguel.gridx = 0;
		gbc_btnCancelarAluguel.gridy = 1;
		panelBtns.add(btnCancelarAluguel, gbc_btnCancelarAluguel);
		
		JButton btnVerTodos = new JButton("Ver Todos");
		btnVerTodos.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		btnVerTodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblTableTitle.setText("Todos Alugu\u00E9is");
				lblTableTitle.setForeground(new Color(200, 50, 50));
				updateTableAlugueis(LocalDate.of(2000, 1, 1));
			}
		});
		GridBagConstraints gbc_btnVerTodos = new GridBagConstraints();
		gbc_btnVerTodos.insets = new Insets(1, 1, 1, 1);
		gbc_btnVerTodos.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnVerTodos.gridx = 0;
		gbc_btnVerTodos.gridy = 2;
		panelBtns.add(btnVerTodos, gbc_btnVerTodos);
		
		JButton btnAlterarContexto = new JButton("Alterar Contexto...");
		GridBagConstraints gbc_btnAlterarContexto = new GridBagConstraints();
		gbc_btnAlterarContexto.insets = new Insets(1, 1, 1, 1);
		gbc_btnAlterarContexto.gridx = 0;
		gbc_btnAlterarContexto.gridy = 3;
		panelBtns.add(btnAlterarContexto, gbc_btnAlterarContexto);
		btnAlterarContexto.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		btnAlterarContexto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisFrame.dispose();
				try {
					SelecionaContextoDialog dialog = new SelecionaContextoDialog();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
		JPanel panelSubtitulo = new JPanel();
		panelSubtitulo.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		contentPane.add(panelSubtitulo, BorderLayout.NORTH);
		
		JLabel lblSubtitulo = new JLabel("Gest\u00E3o de Alugu\u00E9is   [contexto :  "+data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+"]");
		lblSubtitulo.setForeground(Color.BLUE);
		lblSubtitulo.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 24));
		panelSubtitulo.add(lblSubtitulo);
		
		panelTable = new JPanel();
		panelTable.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		contentPane.add(panelTable, BorderLayout.CENTER);
		panelTable.setLayout(new BorderLayout(0, 0));
		
		lblTableTitle = new JLabel("Pr\u00F3ximos Alugu\u00E9is");
		lblTableTitle.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 17));
		panelTableTitle = new JPanel();
		panelTableTitle.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelTableTitle.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panelTableTitle.add(lblTableTitle);
		panelInfo = new JPanel();
//TODO Apagar	
/*ativado em fase de desenvolvimento de GUI
		String[] colunas = {"Id","Data", "Cliente", "Mesas", "Cadeiras"};
		String[][] dados = new String[1][5];

		tableAlugueis = new JTable();
		tableAlugueis.setModel(new DefaultTableModel(dados,colunas) {
				boolean[] columnEditables = new boolean[] {
					false, false, false, false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
		tableAlugueis.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tableAlugueis.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scrollPaneTable = new JScrollPane(tableAlugueis);
		panelTable.add(panelTableTitle, BorderLayout.NORTH);
		panelTable.add(scrollPaneTable, BorderLayout.CENTER);
		panelTable.add(panelInfo, BorderLayout.SOUTH);
		//*/
		//ativado em deploy
		updateTableAlugueis(data);
		
		panelInfo.setLayout(new BorderLayout(0, 0));
		JPanel panelInfoTitle = new JPanel();
		panelInfoTitle.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panelInfo.add(panelInfoTitle, BorderLayout.NORTH);
		
		JLabel lblInfoTitle = new JLabel("Informa\u00E7\u00F5es");
		lblInfoTitle.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 17));
		panelInfoTitle.add(lblInfoTitle);
		
		JPanel panelInfoContent = new JPanel();
		panelInfo.add(panelInfoContent, BorderLayout.CENTER);
		GridBagLayout gbl_panelInfoContent = new GridBagLayout();
		gbl_panelInfoContent.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panelInfoContent.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panelInfoContent.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelInfoContent.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelInfoContent.setLayout(gbl_panelInfoContent);
		
		JLabel lblCadeirasDisponveisHoje = new JLabel("Cadeiras Dispon\u00EDveis Hoje :");
		lblCadeirasDisponveisHoje.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		GridBagConstraints gbc_lblCadeirasDisponveisHoje = new GridBagConstraints();
		gbc_lblCadeirasDisponveisHoje.insets = new Insets(3, 5, 3, 5);
		gbc_lblCadeirasDisponveisHoje.gridx = 0;
		gbc_lblCadeirasDisponveisHoje.gridy = 0;
		panelInfoContent.add(lblCadeirasDisponveisHoje, gbc_lblCadeirasDisponveisHoje);
		
		lblQtdCadDisp = new JLabel();
		lblQtdCadDisp.setForeground(Color.BLUE);
		lblQtdCadDisp.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 14));
		GridBagConstraints gbc_lblQtdCadDisp = new GridBagConstraints();
		gbc_lblQtdCadDisp.insets = new Insets(3, 5, 3, 5);
		gbc_lblQtdCadDisp.gridx = 1;
		gbc_lblQtdCadDisp.gridy = 0;
		panelInfoContent.add(lblQtdCadDisp, gbc_lblQtdCadDisp);
		
		progressBarCadDisp = new JProgressBar();
		progressBarCadDisp.setToolTipText("Porcentagem de Cadeiras Dispon\u00EDveis\r\n");
		progressBarCadDisp.setStringPainted(true);
		progressBarCadDisp.setForeground(SystemColor.textHighlight);
		GridBagConstraints gbc_progressBarCadDisp = new GridBagConstraints();
		gbc_progressBarCadDisp.insets = new Insets(3, 0, 3, 5);
		gbc_progressBarCadDisp.gridx = 2;
		gbc_progressBarCadDisp.gridy = 0;
		panelInfoContent.add(progressBarCadDisp, gbc_progressBarCadDisp);
		
		JLabel lblTotalCadeiras = new JLabel("Total Cadeiras:");
		lblTotalCadeiras.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		GridBagConstraints gbc_lblTotalCadeiras = new GridBagConstraints();
		gbc_lblTotalCadeiras.insets = new Insets(3, 15, 3, 5);
		gbc_lblTotalCadeiras.gridx = 3;
		gbc_lblTotalCadeiras.gridy = 0;
		panelInfoContent.add(lblTotalCadeiras, gbc_lblTotalCadeiras);
		
		lblQtdTotCad = new JLabel();
		lblQtdTotCad.setForeground(Color.BLUE);
		lblQtdTotCad.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 14));
		GridBagConstraints gbc_lblQtdTotCad = new GridBagConstraints();
		gbc_lblQtdTotCad.insets = new Insets(3, 5, 3, 5);
		gbc_lblQtdTotCad.gridx = 4;
		gbc_lblQtdTotCad.gridy = 0;
		panelInfoContent.add(lblQtdTotCad, gbc_lblQtdTotCad);
		
		JLabel lblMesasDisponveisHoje = new JLabel("Mesas Dispon\u00EDveis Hoje:");
		lblMesasDisponveisHoje.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		GridBagConstraints gbc_lblMesasDisponveisHoje = new GridBagConstraints();
		gbc_lblMesasDisponveisHoje.insets = new Insets(3, 5, 3, 5);
		gbc_lblMesasDisponveisHoje.gridx = 0;
		gbc_lblMesasDisponveisHoje.gridy = 1;
		panelInfoContent.add(lblMesasDisponveisHoje, gbc_lblMesasDisponveisHoje);
		
		lblQtdMesDisp = new JLabel();
		lblQtdMesDisp.setForeground(Color.BLUE);
		lblQtdMesDisp.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 14));
		GridBagConstraints gbc_lblQtdMesDisp = new GridBagConstraints();
		gbc_lblQtdMesDisp.insets = new Insets(3, 5, 3, 5);
		gbc_lblQtdMesDisp.gridx = 1;
		gbc_lblQtdMesDisp.gridy = 1;
		panelInfoContent.add(lblQtdMesDisp, gbc_lblQtdMesDisp);
		
		progressBarMesDisp = new JProgressBar();
		progressBarMesDisp.setToolTipText("Porcentagem de Mesas Dispon\u00EDveis\r\n");
		progressBarMesDisp.setStringPainted(true);
		progressBarMesDisp.setForeground(SystemColor.textHighlight);
		GridBagConstraints gbc_progressBarMesDisp = new GridBagConstraints();
		gbc_progressBarMesDisp.insets = new Insets(3, 0, 3, 5);
		gbc_progressBarMesDisp.gridx = 2;
		gbc_progressBarMesDisp.gridy = 1;
		panelInfoContent.add(progressBarMesDisp, gbc_progressBarMesDisp);
		
		JLabel lblTotalMesas = new JLabel("Total Mesas:");
		lblTotalMesas.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		GridBagConstraints gbc_lblTotalMesas = new GridBagConstraints();
		gbc_lblTotalMesas.insets = new Insets(3, 15, 3, 5);
		gbc_lblTotalMesas.gridx = 3;
		gbc_lblTotalMesas.gridy = 1;
		panelInfoContent.add(lblTotalMesas, gbc_lblTotalMesas);
		
		lblQtdTotMes = new JLabel();
		lblQtdTotMes.setForeground(Color.BLUE);
		lblQtdTotMes.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 14));
		GridBagConstraints gbc_lblQtdTotMes = new GridBagConstraints();
		gbc_lblQtdTotMes.insets = new Insets(3, 5, 3, 5);
		gbc_lblQtdTotMes.gridx = 4;
		gbc_lblQtdTotMes.gridy = 1;
		panelInfoContent.add(lblQtdTotMes, gbc_lblQtdTotMes);
		
		JLabel lblJogosDisponveisHoje = new JLabel("Jogos Dispon\u00EDveis Hoje:");
		lblJogosDisponveisHoje.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		GridBagConstraints gbc_lblJogosDisponveisHoje = new GridBagConstraints();
		gbc_lblJogosDisponveisHoje.insets = new Insets(3, 5, 3, 5);
		gbc_lblJogosDisponveisHoje.gridx = 0;
		gbc_lblJogosDisponveisHoje.gridy = 2;
		panelInfoContent.add(lblJogosDisponveisHoje, gbc_lblJogosDisponveisHoje);
		
		lblQtdJogDisp = new JLabel();
		lblQtdJogDisp.setForeground(Color.BLUE);
		lblQtdJogDisp.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 14));
		GridBagConstraints gbc_lblQtdJogDisp = new GridBagConstraints();
		gbc_lblQtdJogDisp.insets = new Insets(3, 5, 3, 5);
		gbc_lblQtdJogDisp.gridx = 1;
		gbc_lblQtdJogDisp.gridy = 2;
		panelInfoContent.add(lblQtdJogDisp, gbc_lblQtdJogDisp);
		
		progressBarJogDisp = new JProgressBar();
		progressBarJogDisp.setToolTipText("Porcentagem de Jogos Dispon\u00EDveis");
		progressBarJogDisp.setStringPainted(true);
		progressBarJogDisp.setForeground(SystemColor.textHighlight);
		GridBagConstraints gbc_progressBarJogDisp = new GridBagConstraints();
		gbc_progressBarJogDisp.insets = new Insets(3, 0, 3, 5);
		gbc_progressBarJogDisp.gridx = 2;
		gbc_progressBarJogDisp.gridy = 2;
		panelInfoContent.add(progressBarJogDisp, gbc_progressBarJogDisp);
		
		JLabel lblTotalJogos = new JLabel("Total Jogos:");
		lblTotalJogos.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		GridBagConstraints gbc_lblTotalJogos = new GridBagConstraints();
		gbc_lblTotalJogos.insets = new Insets(3, 15, 3, 5);
		gbc_lblTotalJogos.gridx = 3;
		gbc_lblTotalJogos.gridy = 2;
		panelInfoContent.add(lblTotalJogos, gbc_lblTotalJogos);
		
		lblQtdTotJog = new JLabel();
		lblQtdTotJog.setForeground(Color.BLUE);
		lblQtdTotJog.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 14));
		GridBagConstraints gbc_lblQtdTotJog = new GridBagConstraints();
		gbc_lblQtdTotJog.insets = new Insets(3, 5, 3, 5);
		gbc_lblQtdTotJog.gridx = 4;
		gbc_lblQtdTotJog.gridy = 2;
		panelInfoContent.add(lblQtdTotJog, gbc_lblQtdTotJog);
		
		updatePanelInfo(data);
	}
	
	@SuppressWarnings("serial")
	private void updateTableAlugueis(LocalDate data) {
		try {
			String[] colunas = {"Id","Data", "Cliente", "Mesas", "Cadeiras"};
			String[][] dados = dao.dadosAlugueisAPartirDe(data);
			
			tableAlugueis = new JTable();
			tableAlugueis.setModel(new DefaultTableModel(dados,colunas) {
					boolean[] columnEditables = new boolean[] {
						false, false, false, false, false
					};
					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				});
			tableAlugueis.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			tableAlugueis.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			JScrollPane scrollPaneTable = new JScrollPane(tableAlugueis);
			panelTable.removeAll();
			panelTable.add(panelTableTitle, BorderLayout.NORTH);
			panelTable.add(scrollPaneTable, BorderLayout.CENTER);
			panelTable.add(panelInfo, BorderLayout.SOUTH);
			contentPane.updateUI();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void updatePanelInfo(LocalDate data) {
		Integer tCad, cadD, tJog, jogD, tMes, mesD;
		estoque = DiaAluguel.of(connection, data).getEstoqueJogos();

		tCad = estoque.getEstoqueCadeiras().getTotalCadeiras();
		cadD = estoque.getEstoqueCadeiras().getCadeirasDisponiveis();
		tMes = estoque.getEstoqueMesas().getTotalMesas();
		mesD = estoque.getEstoqueMesas().getMesasDisponiveis();
		tJog = estoque.getTotalJogos();
		jogD = estoque.getJogosDisponiveis();
		
		lblQtdCadDisp.setText(cadD.toString());
		lblQtdMesDisp.setText(mesD.toString());
		lblQtdJogDisp.setText(jogD.toString());
		
		lblQtdTotCad.setText(tCad.toString());
		lblQtdTotMes.setText(tMes.toString());
		lblQtdTotJog.setText(tJog.toString());
		
		progressBarCadDisp.setMinimum(0);
		progressBarCadDisp.setMaximum(tCad);
		progressBarCadDisp.setValue(cadD);
		
		progressBarMesDisp.setMinimum(0);
		progressBarMesDisp.setMaximum(tMes);
		progressBarMesDisp.setValue(mesD);
		
		progressBarJogDisp.setMinimum(0);
		progressBarJogDisp.setMaximum(tJog);
		progressBarJogDisp.setValue(jogD);
	}

}
