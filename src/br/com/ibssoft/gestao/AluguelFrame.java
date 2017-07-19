
package br.com.ibssoft.gestao;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import br.com.ibssoft.database.AlugueisDAO;
import br.com.ibssoft.gestao.aluguel.Aluguel;

public class AluguelFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableAlugueis;
	private AlugueisDAO dao;
	
	/**
	 * Create the frame.
	 */
	public AluguelFrame(LocalDate data) {
		setTitle("Aluguel Mesas & Cadeiras");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelBtns = new JPanel();
		contentPane.add(panelBtns, BorderLayout.EAST);
		GridBagLayout gbl_panelBtns = new GridBagLayout();
		gbl_panelBtns.columnWidths = new int[]{0, 0};
		gbl_panelBtns.rowHeights = new int[]{0, 0, 0};
		gbl_panelBtns.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panelBtns.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelBtns.setLayout(gbl_panelBtns);
		
		JButton btnNovoAluguel = new JButton("Novo Aluguel");
		GridBagConstraints gbc_btnNovoAluguel = new GridBagConstraints();
		gbc_btnNovoAluguel.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNovoAluguel.insets = new Insets(0, 0, 5, 0);
		gbc_btnNovoAluguel.gridx = 0;
		gbc_btnNovoAluguel.gridy = 0;
		panelBtns.add(btnNovoAluguel, gbc_btnNovoAluguel);
		
		JButton btnCancelarAluguel = new JButton("Cancelar Aluguel");
		GridBagConstraints gbc_btnCancelarAluguel = new GridBagConstraints();
		gbc_btnCancelarAluguel.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCancelarAluguel.gridx = 0;
		gbc_btnCancelarAluguel.gridy = 1;
		panelBtns.add(btnCancelarAluguel, gbc_btnCancelarAluguel);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.WEST);
		
		JPanel panelSubtitulo = new JPanel();
		contentPane.add(panelSubtitulo, BorderLayout.NORTH);
		
		JLabel lblSubtitulo = new JLabel("<Data-Contexto>");
		panelSubtitulo.add(lblSubtitulo);
		
		JPanel panelTable = new JPanel();
		contentPane.add(panelTable, BorderLayout.CENTER);
		panelTable.setLayout(new BorderLayout(0, 0));
		
		//TODO Configurar table
		String[] colunas = {"Data", "Cliente", "Mesas", "Cadeiras"};
		String[][] dados = null;
		try {
			dados = dao.dadosAlugueisAPartirDe(data);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(dados!=null){
			tableAlugueis = new JTable(dados, colunas);
			panelTable.add(tableAlugueis);
		}else{
			panelTable.add(new JLabel("Nenhum aluguel encontrado!"));
		}
		JPanel panelTableTitle = new JPanel();
		panelTable.add(panelTableTitle, BorderLayout.NORTH);
		
		JLabel lblTableTitle = new JLabel("Pr\u00F3ximos Alugu\u00E9is");
		panelTableTitle.add(lblTableTitle);
	}

}
