package br.com.ibssoft.teste;

import java.time.LocalDate;

import br.com.ibssoft.gestao.AluguelFrame;

public class TestaAluguelFrame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LocalDate data = LocalDate.now();
		try {
			AluguelFrame frame = new AluguelFrame(data);
			//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);  // Maximiza ao iniciar
			frame.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
