function f(byref n as integer) as integer
	if n = 0 then
		return 1
	else
   		return n * (f(n-1))
	end if
end function

sub main()
	console.write("Introduzca un numero")
	dim i as integer
	i = console.read()	
                             console.write("El Factorial es:\n")
 	console.write(f(i))
end sub
