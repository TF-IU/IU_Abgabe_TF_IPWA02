package config;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@RequestScoped
public class EMProducer{

    @Inject
    private EMFProvider emfProvider;

    @Produces
    public EntityManager produceEntityManager() {
        return emfProvider.getEmf().createEntityManager();
    }
}
