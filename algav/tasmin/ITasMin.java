package algav.tasmin;

import java.util.List;

/**
 * Interface pour les tas min
 *
 * @param <T> type contenu dans le tas
 */
public interface ITasMin<T> {

    public T supprMin();

    public ITasMin<T> ajout(T cle);

    public void consIter(List<T> cles);

    public ITasMin<T> union(ITasMin<T> tas);

    public List<T> getListe();

    public boolean estVide();
}
