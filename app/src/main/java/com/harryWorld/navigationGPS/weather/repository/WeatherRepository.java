package com.harryWorld.navigationGPS.weather.repository;


import com.harryWorld.navigationGPS.map.retrofit.Direction;
import com.harryWorld.navigationGPS.map.retrofit.GO;
import com.harryWorld.navigationGPS.map.retrofit.Geocode;
import com.harryWorld.navigationGPS.weather.api.Climate;
import com.harryWorld.navigationGPS.weather.request.GeocoderGenerator;
import com.harryWorld.navigationGPS.weather.request.ServiceGenerator;
import com.harryWorld.navigationGPS.weather.utils.Alert;
import com.harryWorld.navigationGPS.weather.utils.Resource;
import com.harryWorld.navigationGPS.weather.utils.Weather;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;

import static com.harryWorld.navigationGPS.weather.api.ClimateConstant.CLIMATE_API;
import static com.harryWorld.navigationGPS.weather.constant.Constant.API_KEY;
import static com.harryWorld.navigationGPS.weather.constant.Constant.GEOCODE_API_KEY;


public class WeatherRepository {
    Weather weather;
    Alert alert;
    Geocode geocode;
    GO going;
    Direction direction;
    private static WeatherRepository weatherRepository;
    private String language;
    private String lang;

    private TimeUnit timeUnit = TimeUnit.SECONDS;
    int timeDelay = 15;

    public static WeatherRepository getWeatherRepository() {
        if (weatherRepository == null) {
            return weatherRepository = new WeatherRepository();
        }
        return weatherRepository;
    }

    private void gettingLanguage(){
        switch (Locale.getDefault().getLanguage()){
            case "de":{
                language = "de";
                lang = "de-DE";
            }
            break;
            case "es":{
                language = "es";
                lang = "es-ES";
            }
            break;
            case "ar":{
                language = "ar";
                lang = "ar";
            }
            break;
            case "zh":{
                language = "zh";
                lang = "zh-CN";
            }
            break;
            case "pt":{
                language = "pt";
                lang = "pt-BR";
            }
            break;
            case "fr":{
                language = "fr";
                lang = "fr-FR";
            }
            break;
            default:{
                language = "en";
                lang = "en-US";
            }
            break;
        }
    }
    public WeatherRepository() {
        gettingLanguage();
        weather = new Weather();
        alert = new Alert();
        geocode = new Geocode();
        going = new GO();
        direction = new Direction();
    }

    //getting weather hourly
    public Flowable<Resource<List<Weather>>> getWeatherHourlyKo(Double latitude, Double longitude, int hours) {
        return ServiceGenerator.request.getWeatherHourlyKo(latitude, longitude, language, hours, API_KEY)
                //  .delaySubscription(timeDelay,timeUnit)
                .map(new Function<Resource<List<Weather>>, List<Weather>>() {
                    @Override
                    public List<Weather> apply(Resource<List<Weather>> specificsWeatherResource) {
                        return specificsWeatherResource.data;
                    }
                })
                .onErrorReturn(new Function<Throwable, List<Weather>>() {
                    @Override
                    public List<Weather> apply(Throwable throwable) throws Exception {
                        String name = throwable.getMessage();
                        List<Weather> list = new ArrayList<>();
                        if (weather != null) {
                            // weather.setCityName(name);
                            weather.setErrorReturn(-1);
                            weather.setErrorMessage(name);
                            list.add(weather);
                        }
                        return list;
                    }
                })
                .map(new Function<List<Weather>, Resource<List<Weather>>>() {
                    @Override
                    public Resource<List<Weather>> apply(List<Weather> weathers) throws Exception {
                        if (weathers != null && weathers.get(0).getErrorReturn() == -1) {
                            return Resource.error(weathers, weathers.get(0).getErrorMessage());
                        }
                        else if (weathers != null) {
                            return Resource.success(weathers,null);
                        }
                        return null;
                    }

                })
                .subscribeOn(Schedulers.io());
    }
    public Flowable<Resource<List<Weather>>> getWeatherHourlyCi(String city, int hours) {
        return ServiceGenerator.request.getWeatherHourlyCi(city, hours,language, API_KEY)
                //  .delaySubscription(timeDelay,timeUnit)
                .map(new Function<Resource<List<Weather>>, List<Weather>>() {
                    @Override
                    public List<Weather> apply(Resource<List<Weather>> specificsWeatherResource) {
                        return specificsWeatherResource.data;
                    }
                })
                .onErrorReturn(new Function<Throwable, List<Weather>>() {
                    @Override
                    public List<Weather> apply(Throwable throwable) throws Exception {
                        String name = throwable.getMessage();
                        List<Weather> list = new ArrayList<>();
                        if (weather != null) {
                            // weather.setCityName(name);
                            weather.setErrorReturn(-1);
                            weather.setErrorMessage(name);
                            list.add(weather);
                        }
                        return list;
                    }
                })
                .map(new Function<List<Weather>, Resource<List<Weather>>>() {
                    @Override
                    public Resource<List<Weather>> apply(List<Weather> weathers) throws Exception {
                        if (weathers != null && weathers.get(0).getErrorReturn() == -1) {
                            return Resource.error(weathers, weathers.get(0).getErrorMessage());
                        }
                        else if (weathers != null) {
                            return Resource.success(weathers,null);
                        }
                        return null;
                    }

                })
                .subscribeOn(Schedulers.io());
    }

    //getting weather daily
    public Flowable<Resource<List<Weather>>> getWeatherDailyKo(Double latitude, Double longitude) {
        return ServiceGenerator.request.getWeatherDailyKo(latitude, longitude, language, API_KEY)
                //  .delaySubscription(timeDelay,timeUnit)
               .map(new Function<Resource<List<Weather>>, List<Weather>>() {
                   @Override
                   public List<Weather> apply(Resource<List<Weather>> listWeatherResource) throws Exception {
                       return listWeatherResource.data;
                   }
               })
                .onErrorReturn(new Function<Throwable, List<Weather>>() {
                    @Override
                    public List<Weather> apply(Throwable throwable) throws Exception {
                        String name = throwable.getMessage();
                        List<Weather> weatherList = new ArrayList<>();
                        if (weather != null) {
                            // weather.setCityName(name);
                            weather.setErrorReturn(-1);
                            weather.setErrorMessage(name);
                            weatherList.add(weather);
                        }
                        return weatherList;
                    }
                })
                .map(new Function<List<Weather>, Resource<List<Weather>>>() {
                    @Override
                    public Resource<List<Weather>> apply(List<Weather> weathers) throws Exception {
                        if (weathers != null && weathers.get(0).getErrorReturn() == -1) {
                            return Resource.error(weathers, weathers.get(0).getErrorMessage());
                        }
                        else if (weathers != null) {
                            return Resource.success(weathers,null);
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io());
    }
    public Flowable<Resource<List<Weather>>> getWeatherDailyCi(String city) {
        return ServiceGenerator.request.getWeatherDailyCi(city, language, API_KEY)
                //  .delaySubscription(timeDelay,timeUnit)
                .map(new Function<Resource<List<Weather>>, List<Weather>>() {
                    @Override
                    public List<Weather> apply(Resource<List<Weather>> specificsWeatherResource) throws Exception {
                        return specificsWeatherResource.data;
                    }
                })
                .onErrorReturn(new Function<Throwable, List<Weather>>() {
                    @Override
                    public List<Weather> apply(Throwable throwable) throws Exception {
                        String name = throwable.getMessage();
                        List<Weather> list = new ArrayList<>();
                        if (weather != null) {
                            // weather.setCityName(name);
                            weather.setErrorReturn(-1);
                            weather.setErrorMessage(name);
                            list.add(weather);
                        }
                        return list;
                    }
                })
                .map(new Function<List<Weather>, Resource<List<Weather>>>() {
                    @Override
                    public Resource<List<Weather>> apply(List<Weather> weathers) throws Exception {
                        if (weathers != null ) {
                            if ( weathers.get(0).getErrorReturn() == -1) {
                                return Resource.error(weathers, weathers.get(0).getErrorMessage());
                            }
                            else {
                                return Resource.success(weathers,null);
                            }
                        }

                        return null;
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    //getting current weather
    public Flowable<Resource<List<Weather>>> getWeatherCurrentKo(Double latitude, Double longitude) {
        return ServiceGenerator.request.getWeatherCurrentKo(latitude, longitude, language, API_KEY)
                //  .delaySubscription(timeDelay,timeUnit)
                .map(new Function<Resource<List<Weather>>, List<Weather>>() {
                    @Override
                    public List<Weather> apply(Resource<List<Weather>> specificsWeatherResource) throws Exception {
                        return specificsWeatherResource.data;
                    }
                })
                .onErrorReturn(new Function<Throwable, List<Weather>>() {
                    @Override
                    public List<Weather> apply(Throwable throwable) throws Exception {
                        String name = throwable.getMessage();
                        List<Weather> list = new ArrayList<>();
                        if (weather != null) {
                            // weather.setCityName(name);
                            weather.setErrorReturn(-1);
                            weather.setErrorMessage(name);
                            list.add(weather);
                        }
                        return list;
                    }
                })
                .map(new Function<List<Weather>, Resource<List<Weather>>>() {
                    @Override
                    public Resource<List<Weather>> apply(List<Weather> weathers) throws Exception {
                        if (weathers != null && weathers.get(0).getErrorReturn() == -1) {
                            return Resource.error(weathers, weathers.get(0).getErrorMessage());
                        }
                        else if (weathers != null) {
                            return Resource.success(weathers,null);
                        }
                        return null;
                    }

                })
                .subscribeOn(Schedulers.io());
    }
    public Flowable<Resource<List<Weather>>> getWeatherCurrentCi(String city) {
        return ServiceGenerator.request.getWeatherCurrentCi(city, language, API_KEY)
                //  .delaySubscription(timeDelay,timeUnit)
                .map(new Function<Resource<List<Weather>>, List<Weather>>() {
                    @Override
                    public List<Weather> apply(Resource<List<Weather>> specificsWeatherResource) throws Exception {
                        return specificsWeatherResource.data;
                    }
                })
                .onErrorReturn(new Function<Throwable, List<Weather>>() {
                    @Override
                    public List<Weather> apply(Throwable throwable) throws Exception {
                        String name = throwable.getMessage();
                        List<Weather> list = new ArrayList<>();
                        if (weather != null) {
                            // weather.setCityName(name);
                            weather.setErrorReturn(-1);
                            weather.setErrorMessage(name);
                            list.add(weather);
                        }
                        return list;
                    }
                })
                .map(new Function<List<Weather>, Resource<List<Weather>>>() {
                    @Override
                    public Resource<List<Weather>> apply(List<Weather> weathers) throws Exception {
                        if (weathers != null && weathers.get(0).getErrorReturn() == -1) {
                            return Resource.error(weathers, weathers.get(0).getErrorMessage());
                        }
                        else if (weathers != null) {
                            return Resource.success(weathers,null);
                        }
                        return null;
                    }

                })
                .subscribeOn(Schedulers.io());
    }

    //getting alert
    public Flowable<Resource<List<Alert>>> getWeatherAlertKo(Double latitude, Double longitude) {
        return ServiceGenerator.request.getWeatherAlertKo(latitude, longitude, API_KEY)
                .map(new Function<Resource<List<Alert>>, List<Alert>>() {
                    @Override
                    public List<Alert> apply(Resource<List<Alert>> listWeatherResource) throws Exception {
                        return listWeatherResource.data;
                    }
                })
                .onErrorReturn(new Function<Throwable, List<Alert>>() {
                    @Override
                    public List<Alert> apply(Throwable throwable) throws Exception {
                        String name = throwable.getMessage();
                        List<Alert> list = new ArrayList<>();
                        if (alert != null) {
                            alert.setError(name);
                            alert.setInError(-1);
                            list.add(alert);
                        }
                        return list;
                    }
                })
                .map(new Function<List<Alert>, Resource<List<Alert>>>() {
                    @Override
                    public Resource<List<Alert>> apply(List<Alert> alerts) throws Exception {
                        if (alerts != null && alerts.get(0).getInError() == -1) {
                            return Resource.error(alerts, alerts.get(0).getError());
                        } else if (alerts != null){
                            return Resource.success(alerts,null);
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io());
    }
    public Flowable<Resource<List<Alert>>> getWeatherAlertCi(String city) {
        return ServiceGenerator.request.getWeatherAlertCi(city, API_KEY)
                .map(new Function<Resource<List<Alert>>, List<Alert>>() {
                    @Override
                    public List<Alert> apply(Resource<List<Alert>> listWeatherResource) throws Exception {
                        return listWeatherResource.data;
                    }
                })
                .onErrorReturn(new Function<Throwable, List<Alert>>() {
                    @Override
                    public List<Alert> apply(Throwable throwable) throws Exception {
                        String name = throwable.getMessage();
                        List<Alert> list = new ArrayList<>();
                        if (alert != null) {
                            alert.setError(name);
                            alert.setInError(-1);
                            list.add(alert);
                        }
                        return list;
                    }
                })
                .map(new Function<List<Alert>, Resource<List<Alert>>>() {
                    @Override
                    public Resource<List<Alert>> apply(List<Alert> alerts) throws Exception {
                        if (alerts != null && alerts.get(0).getInError() == -1) {
                            return Resource.error(alerts, alerts.get(0).getError());
                        } else if (alerts != null){
                            return Resource.success(alerts,null);
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io());
    }
    public Flowable<Resource<GO>> getGeocode(String address){
        return GeocoderGenerator.request.getGeocode(address+".json",GEOCODE_API_KEY,lang)
                .map(new Function<Resource<GO>, GO>() {
                    @Override
                    public GO apply(Resource<GO> geocodeResource) throws Exception {
                        return geocodeResource.data;
                    }
                })
                .onErrorReturn(new Function<Throwable, GO>() {
                    @Override
                    public GO apply(Throwable throwable) throws Exception {
                        String name = throwable.getMessage();
                        List<Geocode> geocodeList = new ArrayList<>();
                            going.setErrorMessage(name);
                            going.setErrorReturn(-1);

                        return going;
                    }
                })
                .map(new Function<GO, Resource<GO>>() {
                    @Override
                    public Resource<GO> apply(GO geocode) throws Exception {
                        if (geocode!=null) {
                            if (geocode != null && geocode.getErrorReturn() == -1) {
                                return Resource.error(geocode, geocode.getErrorMessage());
                            }
                            return Resource.success(geocode, null);
                        }
                        return Resource.success(null, null);
                    }
                })
                .subscribeOn(Schedulers.io());
    }
    public Flowable<Resource<List<Geocode>>> getReverseGeocode(String address){
        return GeocoderGenerator.request.getReverseGeocode(GEOCODE_API_KEY, language, address)
                .map(new Function<Resource<List<Geocode>>, List<Geocode>>() {
                    @Override
                    public List<Geocode> apply(Resource<List<Geocode>> geocodeResource) throws Exception {
                        return geocodeResource.data;
                    }
                })
                .onErrorReturn(new Function<Throwable, List<Geocode>>() {
                    @Override
                    public List<Geocode> apply(Throwable throwable) throws Exception {
                        String name = throwable.getMessage();
                        List<Geocode> geocodeList = new ArrayList<>();
                        if (geocode != null) {
                            geocode.setErrorMessage(name);
                            geocode.setErrorReturn(-1);
                            geocodeList.add(geocode);

                        }

                        return geocodeList;
                    }
                })
                .map(new Function<List<Geocode>, Resource<List<Geocode>>>() {
                    @Override
                    public Resource<List<Geocode>> apply(List<Geocode> geocode) throws Exception {
                        if (geocode != null && geocode.size() > 0) {
                            if (geocode.get(0).getErrorReturn() == -1) {
                                return Resource.error(geocode, geocode.get(0).getErrorMessage());
                            }
                            return Resource.success(geocode, null);
                        }
                        return Resource.success(null, null);
                    }
                })
                .subscribeOn(Schedulers.io());
    }
    public Call<GO> getGeocoder(String address){
        return GeocoderGenerator.request.getGeocoder(address+".json", GEOCODE_API_KEY,lang);
    }

    public Call<GO> getReverseGeocoder(String address){
        return GeocoderGenerator.request.getReverseGeocoder(address+".json", GEOCODE_API_KEY,lang);
    }

    public Call<Climate> getClimate(String address){
        return ServiceGenerator.request.getClimate(CLIMATE_API,address,language);
    }
//    public Observable<List<Direction>> getDirection(String origin, String destination){
//        return DirectionGenerator.request.getDirectionApi(destination, origin, String.valueOf(R.string.google_maps_key))
//                ;
//    }

}