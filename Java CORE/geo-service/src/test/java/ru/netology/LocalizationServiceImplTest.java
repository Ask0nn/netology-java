package ru.netology;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationServiceImpl;

public class LocalizationServiceImplTest {

    @Test
    public void testLocaleRu(){
        String expected = "Добро пожаловать";
        LocalizationServiceImpl localizationService = Mockito.mock(LocalizationServiceImpl.class);

        Mockito.when(localizationService.locale(Country.RUSSIA))
                .thenReturn("Добро пожаловать");

        String actual = localizationService.locale(Country.RUSSIA);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testLocaleEn(){
        String expected = "Welcome";
        LocalizationServiceImpl localizationService = Mockito.mock(LocalizationServiceImpl.class);

        Mockito.when(localizationService.locale(Country.USA))
                .thenReturn("Welcome");

        String actual = localizationService.locale(Country.USA);

        Assertions.assertEquals(expected, actual);
    }
}
