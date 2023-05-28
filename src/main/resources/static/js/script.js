document.getElementById('citySelect').addEventListener('change', changeCity);
document.getElementById('measureBtn').addEventListener('click', measure);
document.getElementById('newCityForm').addEventListener('submit', addAndMeasure);

function changeCity() {
    changeDisplayData(document.getElementById('citySelect').value);
}

function changeDisplayData(cityId) {
    if (cityId == 0) {
        document.getElementById('pAvg1').innerText = '';
        document.getElementById('pAvg7').innerText = '';
        document.getElementById('pAvg14').innerText = '';
        document.getElementById('measureBtn').disabled = true;
    }

    avgs.forEach(avg => {
        if (avg.cityId == cityId) {
            document.getElementById('pAvg1').innerText = 'Last day average: ' + avg.avg1;
            document.getElementById('pAvg7').innerText = 'Last 7 days average: ' + avg.avg7;
            document.getElementById('pAvg14').innerText = 'Last 14 days average: ' + avg.avg14;
            document.getElementById('measureBtn').disabled = false;
        }
    });
}

function measure() {
    cityId = document.getElementById('citySelect').value;
    console.log(cityId);

    $.ajax({
        url: 'api/measure/last/' + cityId,
        method: 'GET',
        success: function (response) {
            lastMeasurement = response;
            showLastMeasurement();
            $.ajax({
                url: 'api/avgs',
                method: 'GET',
                success: function (response) {
                    avgs = response;
                    changeDisplayData(cityId);

                },
                error: function (xhr, status, error) {
                    console.log('Error:', error);
                }
            });
        },
        error: function (xhr, status, error) {
            console.log('Error:', error);
        }
    });
}

function addAndMeasure(e) {
    e.preventDefault();
    cityName = document.getElementById('newCityNameInput').value;

    $.ajax({
        url: 'api/measure/' + cityName,
        method: 'GET',
        success: function (response) {
            lastMeasurement = response;
            $.ajax({
                url: 'api/city/all',
                method: 'GET',
                success: function (response) {
                    cities = response;
                    refreshCities();
                    showLastMeasurement();
                    $.ajax({
                        url: 'api/avgs',
                        method: 'GET',
                        success: function (response) {
                            avgs = response;
        
                        },
                        error: function (xhr, status, error) {
                            console.log('Error:', error);
                        }
                    });
                },
                error: function (xhr, status, error) {
                    console.log('Error:', error);
                }
            });
        },
        error: function (xhr, status, error) {
            console.log('Error:', error);
        }
    });
}

function showLastMeasurement() {
    cities.forEach(city => {
        if (city.id == lastMeasurement.cityId) {
            document.getElementById('lastName').innerText = 'City: ' + city.name;
            document.getElementById('lastWhen').innerText = 'Time: ' + lastMeasurement.timestamp;
            document.getElementById('lastTemp').innerText = 'Temperature: ' + lastMeasurement.temp + ' °C';
        }
    });
}

function refreshCities() {
    select = document.getElementById('citySelect');
    select.innerHTML = "";
    var newOption = document.createElement("option");
    newOption.value = 0;
    newOption.text = "";
    select.add(newOption);
    cities.forEach(city => {
        var newOption = document.createElement("option");
        newOption.value = city.id;
        newOption.text = city.name;
        select.add(newOption);
    });
}

refreshCities();

if (lastMeasurement) {
    showLastMeasurement();
}

document.getElementById('measureBtn').disabled = true;


//window.location.replace('...');