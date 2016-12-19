        .data
_mesg1:        .asciiz "hola"
_true:        .asciiz "true\n"
_false:        .asciiz "false\n"
bufferboolean:   .space 8


        .text
        .globl main


readboolean:
        sw $fp, -4($sp)
        sw $ra, -8($sp)
        move $fp, $sp
        sub $sp, $sp, 12
        li $v0, 8
        la $a0, bufferboolean
        li $a1, 7
        move $t1, $a0
        syscall
        la $t2, _true
        li $t0, 1
loop:
        lb $t3, ($t1)
        lb $t4, ($t2)
        bne $t3, $t4 missmatch
        beq $t3,$zero,checkt2
        addi $t1,$t1,1
        addi $t2,$t2,1
        j loop
missmatch:
        beq $t0, $zero ninguna
        li $t0, 0
        add $t1,$zero,$t1
        la $t2, _false
        j loop
ninguna:
        li $v0, 10
        syscall
checkt2:
        beq $t0,$zero, etiquef
        li $v0, 1
        b etiquefinalreadboolean
etiquef:
        li $v0, 0
etiquefinalreadboolean:
        move $sp, $fp
        lw $fp, -4($sp)
        lw $ra, -8($sp)
        jr $ra


imprimirboolean:
        sw $fp, -4($sp)
        sw $ra, -8($sp)
        move $fp, $sp
        sub $sp, $sp, 12
        beqz $a0, etiquefalsa
        li $v0, 4
        la $a0, _true
        syscall
        b etiquefinalimprimirboolean
etiquefalsa:
        li $v0, 4
        la $a0, _false
        syscall
etiquefinalimprimirboolean:
        move $sp, $fp
        lw $fp, -4($sp)
        lw $ra, -8($sp)
        jr $ra


_e:
        sw $fp, -4($sp)
        sw $ra, -8($sp)
        sw $s0, -12($sp)
        move $s0, $a0
        move $fp, $sp
        sub $sp, $sp, 16
        sub $sp, $sp, 16


       move $v0, $s0
        move $sp, $fp
        lw $s0, -12($sp)
        lw $ra, -8($sp)
        lw $fp, -4($sp)
        jr $ra

_f:
        sw $fp, -4($sp)
        sw $ra, -8($sp)
        move $fp, $sp
        sub $sp, $sp, 12


        li $a0, 0
        jal _e
        li $t0, 1
        beq $v0, $t0, _ETIQUE2
        b _ETIQUE3

_ETIQUE2:
        li $a0, 1
        jal imprimirboolean

        b _ETIQUE1

_ETIQUE3:
        li $a0, 0
        jal imprimirboolean


_ETIQUE1:
        move $sp, $fp
        lw $ra, -8($sp)
        lw $fp, -4($sp)
        jr $ra

main:
        move $fp, $sp

        sub $sp, $sp, 4

        jal _f
        li $v0, 4
        la $a0, _mesg1
        syscall

        li $v0, 10
        syscall
