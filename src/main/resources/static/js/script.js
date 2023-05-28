document.getElementById('citySelect').addEventListener('change', changeCity);
document.getElementById('measureBtn').addEventListener('click', measure);

function changeCity() {
    console.log('changed');
    changeDisplayData(document.getElementById('citySelect').value);
}

function changeDisplayData(cityId){
    avgs.forEach(avg => {
        if(avg.cityId == cityId){
            document.getElementById('pAvg1').innerText = 'Last day average: ' + avg.avg1;
            document.getElementById('pAvg7').innerText = 'Last 7 days average: ' + avg.avg7;
            document.getElementById('pAvg14').innerText = 'Last 14 days average: ' + avg.avg14;
        }
    });
}

function measure(){
    cityId = document.getElementById('citySelect').value;
    console.log(cityId);

    $.ajax({
        url: 'api/measure/last/' + cityId,
        method: 'GET',
        success: function(response) {
            lastMeasurement = response;
            showLastMeasurement();
            $.ajax({
                url: 'api/avgs',
                method: 'GET',
                success: function(response) {
                    avgs = response;
                    changeDisplayData(cityId);
                    
                },
                error: function(xhr, status, error) {
                    console.log('Error:', error);
                }
            });
        },
        error: function(xhr, status, error) {
            console.log('Error:', error);
        }
    });
}

function showLastMeasurement(){
    cities.forEach(city => {
        if(city.id == lastMeasurement.cityId){
            document.getElementById('lastName').innerText = 'City: ' + city.name;
            document.getElementById('lastWhen').innerText = 'Time: ' + lastMeasurement.timestamp;
            document.getElementById('lastTemp').innerText = 'Temperature: ' + lastMeasurement.temp + ' °C';
        }
    });
}

select = document.getElementById('citySelect');
cities.forEach(city => {
    var newOption = document.createElement("option");
    newOption.value = city.id;
    newOption.text = city.name;
    select.add(newOption);
});

if(lastMeasurement){
    showLastMeasurement();
}


//window.location.replace('...');