// let summury1 = new Tabulator("#summary-1",{
//     layout:"fitColumns",
//     height: "100%",
//     placeholder: "모델이 실행되지 않았습니다.",
//     // headerVisible:false,
//     columns: [
//         // {title: "Runoff Ratio", headerSort: false, field: "ratio" },
//         {title: "Runoff Ratio", headerSort: false, columns: [
//                 { title: "Runoff Ratio", field: "ratio" }
//             ]},
//         {title: "Sum of areal rainfall", headerSort: false, field: "realRainfall", editable:false},
//         {title: "Sum of observed flow depth", headerSort: false, field: "observedFlowDept", editable:false},
//         {title: "Sum of computed flow depth", headerSort: false, field: "computedFlowDept", editable:false},
//         {title: "Sum of evapotranspiration", headerSort: false, field: "evapotranspiration", editable:false}
//     ]
//     // columns: [
//     //     {
//     //         title: "Runoff Ratio",
//     //         headerSort: false, // Disable sorting on the group header
//     //         colspan: 2,
//     //         columns: [
//     //             { title: "Runoff Ratio",headerSort: false, field: "ratio" }
//     //         ]
//     //     },
//     //     {
//     //         headerSort: false, // Disable sorting on the group header
//     //         colspan: 2,
//     //         field: "ratio",
//     //         columns: [
//     //             { title: "Runoff Ratio",headerSort: false, field: "ratio" }
//     //         ]
//     //     },
//     //     {
//     //         title: "Sum of areal rainfall",
//     //         headerSort: false, // Disable sorting on the group header
//     //         // columns: [
//     //         //     { title: "Sum of areal rainfall", field: "realRainfall", editable: false }
//     //         // ]
//     //     },
//     //     {title: "Sum of observed flow depth", headerSort: false, field: "observedFlowDept", editable:false},
//     //     {title: "Sum of computed flow depth", headerSort: false, field: "computedFlowDept", editable:false},
//     //     {title: "Sum of evapotranspiration", headerSort: false, field: "evapotranspiration", editable:false}
//     // ]
// });
//
// let summury2 = new Tabulator("#summary-2",{
//     layout:"fitColumns",
//     height: "100%",
//     placeholder: "모델이 실행되지 않았습니다.",
//     columns: [
//         {title: "OBS. MEAN", field: "obsMean"},
//         {title: "OBS. SDEV.", field: "obsSdev"},
//         {title: "SIM. MEAN", field: "simMean"},
//         {title: "SIM. SDEV.", field: "simSdev"}
//     ]
// });

// const estimatedChart = AmCharts.makeChart("chartDiv", {
//         "type": "serial",
//         "categoryField": "date",    // 날짜 필드를 카테고리로 사용
//         "autoMargins": false,
//         "marginLeft": 90,
//         "categoryAxis": {
//             "gridPosition": "start",
//             "labelsEnabled": false,
//             "minHorizontalGap": 35
//         },
//         "trendLines": [],
//         "graphs": [
//             {
//                 "balloonText": "[[title]] of [[category]]:[[value]]",
//                 "fillAlphas": 1,
//                 "fillColors": "#1993FC",
//                 "id": "AmGraph-1",
//                 "lineThickness": 0,
//                 "title": "graph 1",
//                 "type": "column",
//                 "valueField": "rMm" // rmm 데이터를 사용하여 그래프 표시
//             }
//         ],
//         "guides": [],
//         "valueAxes": [
//             {
//                 "id": "ValueAxis-1",
//                 "reversed": true,
//                 "gridThickness": 0,
//                 "title": "Estimated inflow(CMS) _ qs"
//             }
//         ],
//         "allLabels": [],
//         "balloon": {},
//         "titles": [],
//         "dataProvider": []
//     }
//
// );