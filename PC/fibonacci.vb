Function fibonacci(Byref number As Integer) As Integer
   	 If number = 0 Or number = 1  Then 
      		Return number
    	Else
      			
      		Return fibonacci(number - 1) + fibonacci(number - 2)
	End If
 End Function

Sub Main() 
	Console.write("Introduzca un numero\n")
 	Dim counter As Integer
	Dim Limit As Integer
	Limit = console.read()
	for counter = 0 to Limit
		
		Console.write(fibonacci(counter))
		If counter < Limit Then
			Console.write(",")
		End If
	Next
End Sub
