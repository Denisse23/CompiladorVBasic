        .data
_i:        .byte 0
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


main:
        move $fp, $sp

        jal readboolean
        sb $v0, _i

        lb $a0, _i
        jal imprimirboolean

        li $v0, 10
        syscall
