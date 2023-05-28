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
        url: 'last/' + cityId,
        method: 'GET',
        success: function(response) {
            console.log(response);
            
        },
        error: function(xhr, status, error) {
            // Handle the error response
            console.log('Error:', error);
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



console.log(lastMeasurement);

//window.location.replace('...');