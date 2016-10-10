Type MiNuevoTipo
	a As Integer
	b As String
	c As Double
End Type

Function nuevo(ByVal j As Integer) As String
	If j = 0 Then
		Return "Si"
	Else
		Return nuevo(2 * j - 1)
	End If
End Function

Sub sub1(ByVal variable As MiNuevoTipo)
	Dim i As Integer
	Console.Write(variable)

	
	
	For i = 0 To variable.a
		If i = 3 Then
			Console.Write(nuevo(5))
		End If
	Next

	i = Console.Read()

	Do While i <= variable.a 
		Console.Write(i)
	Loop

End Sub