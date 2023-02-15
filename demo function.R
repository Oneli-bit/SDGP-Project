library(shiny)
library(scales)
library(ggplot2)

function(input,output) {
  
  output$plot = renderPlot({
    if (input$unit_abodmen == "cm") abodmen = input$abodmen else abodmen = input$abodmen*2.54
    
    if (input$unit_weight == "lb") weight = input$weight else weight = input$weight*2.20462262
    
    if (input$unit_height == "cm") height = input$height/2.54 else height = input$height
    
    if (input$worh == "Weight") {
      p = -40.32105+0.90438*abodmen-0.13626*weight
    } else {
      p = -2.287e+01-3.903e+05/(abodmen *height)-1.328e+00*abodmen/height+7.335e+03/height
    }
    bodyfat.judge = function(gender,bodyfat){
      if (gender == "male"){
        if (bodyfat <= 10) return(NA) #"Extremely below normal range! Please check your input."
        if (10 < bodyfat & bodyfat <= 14) return("Essential fat")
        if (14 < bodyfat & bodyfat <= 21) return("Athletes")
        if (21 < bodyfat & bodyfat <= 25) return("Fitness")
        if (25 < bodyfat & bodyfat <= 32) return("Average")
        if (bodyfat > 31 & bodyfat <= 60) return("Obese")
        if (bodyfat > 60) return(NULL)
      }
      else {
        if (bodyfat <= 3) return(NA) #"Extremely below normal range! Please check your input."
        if (3 < bodyfat & bodyfat <= 6) return("Essential fat")
        if (6 < bodyfat & bodyfat <= 14) return("Athletes")
        if (14 < bodyfat & bodyfat <= 18) return("Fitness")
        if (18 < bodyfat & bodyfat <= 25) return("Average")
        if (bodyfat > 25 & bodyfat <= 60) return("Obese")
        if (bodyfat > 60) return(NULL)
      }
    }