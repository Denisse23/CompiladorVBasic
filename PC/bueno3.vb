Type MiNuevoTipo
	a As Integer
	b As String
	c As Double
End Type

Sub sub1(ByVal variable As MiNuevoTipo)
	Dim i As Integer
	Console.Write(variable)

	For i = 0 To variable.a
		If i = 3 Then
			Console.Write("Soy tres")
		End If
	Next

	Do While i <= variable.a 
		Console.Write(i)
	Loop

End Sub