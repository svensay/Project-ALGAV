/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algav.tasmin;

import java.util.List;

/**
 * Interface pour les tas min
 *
 * @param <T> type contenu dans le tas
 */
public interface ITasMin<T> {

    public ITasMin supprMin();

    public ITasMin ajout(T cle);

    public void consIter(List<T> cles);

    public ITasMin union(ITasMin tas);

    public List<T> getListe();
}
