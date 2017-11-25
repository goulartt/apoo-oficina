package tattool.report.jasper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import tattool.domain.model.Service;
import tattool.domain.model.User;
import tattool.rest.consume.UserRest;

public class UsuarioRel {
private String path; //Caminho base
	
	private String pathToReportPackage; // Caminho para o package onde estão armazenados os relatorios Jarper
	
	//Recupera os caminhos para que a classe possa encontrar os relatórios
	public UsuarioRel() {
		this.path = this.getClass().getClassLoader().getResource("").getPath();
		this.pathToReportPackage = this.path + "tattool/report/jasper/";
		System.out.println(path);
	}
	
	
	

	public String getPathToReportPackage() {
		return this.pathToReportPackage;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public void geraRelatorioTodosUsuarios() throws Exception {
		UserRest ur = new UserRest();
		User[]  users = ur.findAllUsers();
		HashMap parametros = new HashMap();
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(
				Arrays.asList(users));
		JasperPrint impressao = null;
		try {
			impressao = JasperFillManager.fillReport(JasperCompileManager.compileReport(this.getPathToReportPackage() + "usuarios.jrxml"), parametros, ds);
			JasperViewer viewer = new JasperViewer(impressao, true);
			viewer.viewReport(impressao, false);
		} catch (JRException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	public void geraRelatorioHistorico(List<Service> services) {
		HashMap parametros = new HashMap();
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(services);
		JasperPrint impressao = null;
		try {
			impressao = JasperFillManager.fillReport(JasperCompileManager.compileReport(this.getPathToReportPackage() + "historicoCliente.jrxml"), parametros, ds);
			JasperViewer viewer = new JasperViewer(impressao, true);
			viewer.viewReport(impressao, false);
		} catch (JRException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
