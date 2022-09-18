package br.gov.rj.rioprevidencia.modulos.auth.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
	FONTE: https://deviniciative.wordpress.com/2020/12/21/hands-on-gerenciamento-de-sessao-com-spring-boot/
*/
@Controller
public class SessionControllerTest {
	
	private static final String SESSION_NOTES = "SESSION_NOTES";

	@GetMapping(value = "/index-notas")
	public String home(final Model model, final HttpSession session) {
		final List<String> notas = (List<String>) session.getAttribute(SESSION_NOTES);
		model.addAttribute("sessionNotes", !CollectionUtils.isEmpty(notas) ? notas : new ArrayList<>());

		return "sessao-notas";
	}

	@PostMapping(value = "/salvar/nota")
	public String salvarNota(@RequestParam("nota") final String nota, final HttpServletRequest request) {

		List<String> notas = (List<String>) request.getSession().getAttribute(SESSION_NOTES);

		if (CollectionUtils.isEmpty(notas)) {
			//LOG.info("Não tem nada na Sessão. Setando o valor inicial");
			notas = new ArrayList<>();
		}

		notas.add(nota);
		request.getSession().setAttribute(SESSION_NOTES, notas);
		return "redirect:/index-notas";
	}

	@PostMapping(value = "/destruir/sessao")
	public String destruirSessao(final HttpServletRequest request) {

		//LOG.info("invalidando a sessao e removendo os dados");
		request.getSession().invalidate();
		return "redirect:/index-notas";
	}
}
