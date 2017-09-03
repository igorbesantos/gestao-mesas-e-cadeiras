package br.com.ibssoft.gestao;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.github.lgooddatepicker.components.DatePicker;



public class SelecionaContextoDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private LocalDate data;
	private JDialog thisDialog;

	/**
	 * Create the dialog.
	 */
	public SelecionaContextoDialog() {
		thisDialog = this;
		setType(Type.POPUP);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("Sele\u00E7\u00E3o de Contexto");
		setBounds(100, 100, 580, 120);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 312, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, 0.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		
		JLabel label_1 = new JLabel("");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 0, 5);
		gbc_label_1.gridx = 0;
		gbc_label_1.gridy = 0;
		contentPanel.add(label_1, gbc_label_1);
		
		JLabel lblSelecioneContexto = new JLabel("Selecione a Contexto da aplica\u00E7\u00E3o:");
		lblSelecioneContexto.setFont(new Font("Microsoft JhengHei UI Light", Font.PLAIN, 18));
		GridBagConstraints gbc_lblSelecioneContexto = new GridBagConstraints();
		gbc_lblSelecioneContexto.anchor = GridBagConstraints.WEST;
		gbc_lblSelecioneContexto.insets = new Insets(0, 0, 0, 5);
		gbc_lblSelecioneContexto.gridx = 2;
		gbc_lblSelecioneContexto.gridy = 0;
		contentPanel.add(lblSelecioneContexto, gbc_lblSelecioneContexto);
		
		
		DatePicker datePicker = new DatePicker();
		datePicker.setDate(LocalDate.now());
		datePicker.getComponentDateTextField().setEditable(false);
		GridBagConstraints gbc_datePicker = new GridBagConstraints();
		gbc_datePicker.insets = new Insets(0, 0, 0, 5);
		gbc_datePicker.anchor = GridBagConstraints.WEST;
		gbc_datePicker.gridx = 3;
		gbc_datePicker.gridy = 0;
		contentPanel.add(datePicker, gbc_datePicker);
		
		JLabel label = new JLabel("");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 0, 5);
		gbc_label.gridx = 4;
		gbc_label.gridy = 0;
		contentPanel.add(label, gbc_label);
		
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				data = datePicker.getDate();
				try {
					AluguelFrame frame = new AluguelFrame(data);
					//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);  // Maximiza ao iniciar
					thisDialog.dispose();
					frame.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		
		
		JButton cancelButton = new JButton("Cancelar");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisDialog.dispose();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	}

}
