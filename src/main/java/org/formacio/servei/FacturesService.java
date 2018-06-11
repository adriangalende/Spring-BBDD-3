package org.formacio.servei;

import org.formacio.domain.Factura;
import org.formacio.domain.LiniaFactura;
import org.formacio.repositori.FacturesRepositori;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class FacturesService {

	
	/*
	 * Aquest metode ha de carregar la factura amb id idFactura i afegir una nova linia amb les dades
	 * passades (producte i totalProducte)
	 * 
	 * S'ha de retornar la factura modificada
	 * 
	 * Per implementar aquest metode necessitareu una referencia (dependencia) a FacturesRepositori
	 */
	@Autowired
	FacturesRepositori facturesRepositori;
	@Autowired
	FidalitzacioService fidalitzacioService;

	public Factura afegirProducte (long idFactura, String producte, int totalProducte) {
		Optional<Factura> optionalFactura = facturesRepositori.findById(idFactura);
		Factura factura = null;
		if (optionalFactura.isPresent()){
			factura = optionalFactura.get();
			LiniaFactura liniaFactura = new LiniaFactura();
			liniaFactura.setProducte(producte);
			liniaFactura.setTotal(totalProducte);
			factura.getLinies().add(liniaFactura);
			facturesRepositori.save(factura);
			notificar(factura);
		}
		return factura;
	}

	public void notificar(Factura factura){
		if(factura.getLinies().size()>3){
			fidalitzacioService.notificaRegal(factura.getClient().getEmail());
		}
	}
}
