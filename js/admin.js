

    $(document).ready(function(){
        
        
    });

    //홈화면 매출요약 차트
    const ctx = document.getElementById('sales').getContext('2d');
    const sales = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: [
            '2024-01','2024-02', '2024-03', '2024-04', '2024-05', '2024-06', '2024-07',
            '2024-08', '2024-09', '2024-10', '2024-11', '2024-12',
            '2025-01', '2025-02', '2025-03', '2025-04', '2025-05'
            ],
            datasets: [{
            label: '월별 매출',
            data: [34, 45, 30, 55, 65, 45, 25, 42, 35, 38, 28, 60, 35, 65, 58, 50, 80],
            backgroundColor: '#A6BFA4',
            borderRadius: 4,
            barThickness: 30
            }]
        },
        options: {
            responsive: true,
            plugins: {
            legend: {
                display: false // label 숨기고 싶으면 true로 바꿔도 돼
            },
            tooltip: {
                callbacks: {
                label: function(context) {
                    return `${context.parsed.y}백만원`;
                }
                }
            }
            },
            scales: {
            y: {
                beginAtZero: true,
                title: {
                display: true,
                text: '매출 (백만원)'
                }
            },
            x: {
                ticks: {
                maxRotation: 0,
                minRotation: 0
                }
            }
            }
        }
});
