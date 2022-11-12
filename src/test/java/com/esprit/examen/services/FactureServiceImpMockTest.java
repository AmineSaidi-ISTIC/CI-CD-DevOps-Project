package com.esprit.examen.services;
import com.esprit.examen.entities.Facture;
import com.esprit.examen.repositories.FactureRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@SpringBootTest

public class FactureServiceImpMockTest {
    @Mock
    FactureRepository FactureRepository;
    @InjectMocks
    FactureServiceImpl FactureService;
    @Test
    public void testRetrieveAllFactures(){
        Facture f = new Facture(120,2020,new Date(2022-03-20),new Date(2022-04-20),true);
        Mockito.when(FactureRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(f));
        assertNotNull(FactureService.retrieveFacture(1L));
        System.out.println("retrieve done");
    }

    @Test
    public void testAddFacture()
    {

        Facture f = new Facture(50,500,new Date(2022-01-20),new Date(2022-02-20),true);
        FactureService.addFacture(f);
        verify(FactureRepository, times(1)).save(f);
        System.out.println("add done");
    }

    @Test
    public void testGetFacture()
    {
        List<Facture> Factures = new ArrayList<Facture>();
        Facture f1 = new Facture(700,3000,new Date(2022-05-20),new Date(2022-06-20),true);
        Facture f2 = new Facture(550,1700,new Date(2022-07-20),new Date(2022-11-20),true);
        Factures.add(f1);
        Factures.add(f2);
        when(FactureRepository.findAll()).thenReturn(Factures);
        System.out.println("get list done");
    }

//    @Test
//    public void testUpdateFactureById()
//    {
//        Facture s = new Facture(100,2000,"2022-01-12","2022-01-20",true);
//        when(FactureRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(s));
//        FactureService.updateFacture(s);
//        System.out.println("update done");
//    }

}