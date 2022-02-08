package ru.netology;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;

public class GeoServiceImplTest {

    @Test
    public void testByIpRu(){
        GeoServiceImpl geoService = Mockito.mock(GeoServiceImpl.class);
        Location expected = new Location("Moscow", Country.RUSSIA, "Lenina", 15);

        Mockito.when(geoService.byIp(GeoServiceImpl.MOSCOW_IP))
                .thenReturn(new Location("Moscow", Country.RUSSIA, "Lenina", 15));

        Location actual = geoService.byIp(GeoServiceImpl.MOSCOW_IP);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testByIpEn(){
        GeoServiceImpl geoService = Mockito.mock(GeoServiceImpl.class);
        Location expected = new Location("New York", Country.USA, " 10th Avenue", 32);

        Mockito.when(geoService.byIp(GeoServiceImpl.NEW_YORK_IP))
                .thenReturn(new Location("New York", Country.USA, " 10th Avenue", 32));

        Location actual = geoService.byIp(GeoServiceImpl.NEW_YORK_IP);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testByIpLocal(){
        GeoServiceImpl geoService = Mockito.mock(GeoServiceImpl.class);
        Location expected = new Location(null, null, null, 0);

        Mockito.when(geoService.byIp(GeoServiceImpl.LOCALHOST))
                .thenReturn(new Location(null, null, null, 0));

        Location actual = geoService.byIp(GeoServiceImpl.LOCALHOST);

        Assertions.assertEquals(expected, actual);
    }
}
