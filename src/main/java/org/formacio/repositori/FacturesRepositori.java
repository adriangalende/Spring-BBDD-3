package org.formacio.repositori;


import org.formacio.domain.Client;
import org.formacio.domain.Factura;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FacturesRepositori extends CrudRepository<Factura, Long> {
	@Query("select sum(linia.total) from Factura factura join factura.linies linia where factura.client.nom = ?1")
	public Number totalClient(String client);

	public List<Factura> findAllByClientNom(String client);
}
