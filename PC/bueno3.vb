Type MiNuevoTipo
	a As Integer
	b As String
	c As Integer
End Type

Function nuevo(ByVal j As Integer) As String
	
	If j = 0 Then
		Return "Si"
	Else
		Return nuevo(2 * j - 1)
	End If
End Function

Dim r as string

Sub main(ByVal variable As Integer)
	Dim r as MiNuevoTipo
	Dim i As Integer
	Console.Write(variable)

	
	
	For i = 0 To variable
		If r.b = 3 Then
			Console.Write(nuevo(5))
		End If
	Next

	i = Console.Read()

	Do While r.a <= variable
		Console.Write(i)
	Loop

End Sub