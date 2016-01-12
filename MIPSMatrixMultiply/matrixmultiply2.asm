	.data
file1: .asciiz  "C:\\Program Files\\MARS\\matrix161.txt"
file2:	.asciiz  "C:\\Program Files\\MARS\\matrix162.txt"

mat1:	.asciiz  "\nMatrix 1: \n"
mat2:	.asciiz  "\nMatrix 2: \n"
mat3:	.asciiz  "\nMatrix 1 X Matrix 2: \n"
mat4:	.asciiz  "\nMatrix 2 transposed: \n"
mat5:	.asciiz	 "\nMatrix 1 X Matrix 2 transposed: \n"

m1: 	.space 1100     #space for numbers


m2:	.space 1100
	.align 2

matrix1:	.space 1188	#Address of matrix 1
	.align 2
matrix2:	.space 1188	#Address of matrix 2
	.align 2
matrix3:	.space 1188	#Address of matrix 3
	.align 2
matrix4:	.space 1188	#Address of matrix 4
	.align 2
matrix5:	.space 1188	#Address of matrix 5
lnbrk:	.asciiz  "\n"
spc:	.asciiz  " "	

buffer: .space 1024
fnf:	.asciiz  "The file was not found: "
      	.text
      	
#####Main
	li	$s7, 16		#set matrix size
	#Load first matrix
      	li	$v0, 4		# Print String Syscall
	la	$a0, mat1	# Load Label
	syscall
      	la	$a0, file1	#Load address of file 1
      	la	$a1, m1		#load matrix1 address
      	la	$a2, matrix1
      	jal	loadm		#Jump to load procedure
      	la	$a0, matrix1	#stor matrix address
      	add	$a1, $s7, $zero	#set MATRIX SIZE
      	jal	printm		#jump to print procedure
      	#load second matrix
      	li	$v0, 4		# Print String Syscall
	la	$a0, mat2	# Load label
	syscall
      	la	$a0, file2	#Load address of file 1
      	la	$a1, m2		#load matrix1 address
      	la	$a2, matrix2
      	jal	loadm		#Jump to load procedure
      	la	$a0, matrix2	#stor matrix address     
      	add	$a1, $s7, $zero	#set MATRIX SIZE	
      	jal	printm		#jump to print procedure
      	
      	##multiply 2 matrices
      	la	$a0, matrix1
      	la	$a1, matrix2
      	la	$a2, matrix3
      	add	$a3, $s7, $zero
      	jal multiplymatrix
      	li	$v0, 4		# Print String Syscall
	la	$a0, mat3	# Load label
	syscall
      	la	$a0, matrix3	#stor matrix address    
      	add	$a1, $s7, $zero	#set MATRIX SIZE 	
      	jal	printm		#jump to print procedure
      	#Transpose matrix 2 to 4 & print matrix 4
      	li	$v0, 4		# Print String Syscall
	la	$a0, mat4	# Load Label
	syscall
      	la	$a0, matrix2
      	la	$a1, matrix4
      	add	$a2, $s7, $zero
      	jal transpose
      	la	$a0, matrix4	#stor matrix address    
      	add	$a1, $s7, $zero	#set MATRIX SIZE 	
      	jal	printm		#jump to print procedure
      	#multiply transposed
      	li	$v0, 4		# Print String Syscall
	la	$a0, mat5	# Load Label
	syscall
	la	$a0, matrix1	#Send matrix 1 address
	la	$a1, matrix4	#send matrix 2 address
	la	$a2, matrix5	#send matrix 3 address
      	add	$a3, $s7, $zero	#send size
      	jal	transmult	#call multiply transposed
      	la	$a0, matrix5	#stor matrix address    
      	add	$a1, $s7, $zero	#set MATRIX SIZE 	
      	jal	printm		#jump to print procedure
      	#Exit
      	li $v0, 10		#Specify the Exit call.
	syscall			#End program
      	
      	
#######Load matrix
loadm:	
      	##Open file
      	#la	$a0, file1	#Load first file to open.
      	add	$s1, $a0, $zero	#move file address
      	add	$s2, $a1, $zero	#move filestring address
      	add	$s3, $a2, $zero	#move matrix address
      	li	$v0, 13		#Load call to open file.
      	li	$a1, 0
      	li	$a2, 0     	
      	syscall
      	blt	$v0, 0, err	# Goto Error
      	add	$s0, $v0, 0	#Save desciptor
      	
      	##Read file
      	add 	$a0, $v0, 0     # load file descriptor
	li 	$v0, 14         #read from file
	add 	$a1, $s2, $zero # allocate space for the bytes loaded
	li $a2, 1000         	# number of bytes to be read
      	syscall
      	

 	##Loading to matrix array
      	add	$t0, $t0, $zero	#clear
loop1:   lb	$t0, 0($s2)	#Load char from address
	beq	$t0, $zero, exit1#At end of string
      	addi	$t0, $t0, -48	#Change to integer	     	
next:	slt	$t1, $t0, $zero	#test for non-integer
	beq	$t1, 1, char	#skip if it is not an int
	sb	$t0, 0($s3)	#store integer in matrix array
	addi	$s3, $s3, 4
char:	addi	$s2, $s2, 1
	j	loop1
exit1:
	li	$v0, 0
      	jr	$ra

########Print matrix
 printm:
 	add	$s0, $a0, $zero	#save address argument
 	add	$s3, $a1, $zero	#Save MATIRIX SIZE argument
 	and	$t0, $t0, $zero	#initialize i = 0
 looppi:	
 	beq	$t0, $s3, exitpi#to i to MATRIX SIZE
 	and	$t1, $t1, $zero	#initialize j = 0
 looppj:	
 	beq	$t1, $s3, exitpj	#to j to MATRIX SIZE
 	add	$s1, $s0, $zero	#reset matrix address
 	sll	$t2, $t1, 2	#multiply j * 4
 	sll	$t3, $t0, 2	#multiply i * 4 * MATRIX SIZE
 	mult	$t3, $s3	#
 	mflo	$t3		#
 	add	$t3, $t3, $t2	#i + j
 	add	$s1, $s1, $t3	#address + i + j
 	lw	$s2, 0($s1)	#Get element at matrix[i][j]
 	add	$a0, $s2, $zero
      	li 	$v0, 1            # print integer
	syscall
 	
 	li	$v0, 4		# Print String Syscall
	la	$a0, spc	# Load space
	syscall
 	addi	$t1, $t1, 1	#j++
 	j looppj
 exitpj:
 	li	$v0, 4		# Print String Syscall
 	add	$a0, $a0, $zero
	la	$a0, lnbrk	# Load line break
	syscall
 	addi	$t0, $t0, 1	#i++
 	j looppi
 exitpi:
 	li	$v0, 0
      	jr	$ra

	
	#### If file doesn't load: Error
err:	li	$v0, 4		# Print String Syscall
	la	$a0, fnf	# Load Error String
	syscall
	li $v0, 10		#Specify the Exit call.
	syscall			#End program
	
	
###multiply matrix
multiplymatrix:
	add	$s0, $a0, $zero	#save address argument 1
	add	$s1, $a1, $zero	#save address argument 2
	add	$s2, $a2, $zero	#save address argument 3
	add	$s7, $a3, $zero	#save matrix size argument
	#Loop 1
	and	$t0, $t0, $zero	#initialize i = 0
loopmi: 
	beq	$t0, $s7, exitmi#branch to exit if i = size
	#Loop 2
	and	$t1, $t1, $zero	#initialize j = 0
loopmj: 
	beq	$t1, $s7, exitmj#branch to exit if j = k
	#Loop 3
	and	$t2, $t2, $zero#initialize k = 0
loopmk:
	beq	$t2, $s7, exitmk#branch to exit if k = size
	#Inner content
	add	$s4, $s0, $zero	#reset address 1
	add	$s5, $s1, $zero	#reset address 2
	add	$s6, $s2, $zero	#reset address 3
	mult	$t0, $s7	#i*size
	mflo	$t3
	mult	$t2, $s7
	mflo	$t4
	add	$t3, $t3, $t2	#i*size+k
	add	$t4, $t1, $t4
	sll	$t3, $t3, 2	#(i*size+k)*4
	add	$s4, $s4, $t3
	sll	$t4, $t4, 2
	add	$s5, $s5, $t4
	lw	$s4, 0($s4)	#get m1[i][k]
	lw	$s5, 0($s5)	#get m2[k][j]
	mult	$t0, $s7	#i*size
	mflo	$t5
	add	$t5, $t5, $t1
	sll	$t5, $t5, 2
	add	$s6, $s6, $t5
	lw	$t5, 0($s6)
	mult	$s4, $s5
	mflo	$s4
	add	$t5, $t5, $s4
	sw	$t5, 0($s6)
	addi	$t2, $t2, 1	#k++
	j loopmk		#jump to loop
exitmk:
	addi	$t1, $t1, 1	#j++
	j loopmj		#jump to loop
exitmj:
	addi	$t0, $t0, 1	#i++
	j loopmi		#jump to loop
exitmi:
	li	$v0, 0
      	jr	$ra		#return to main

	

########Transpose MAtrix
transpose:
 	add	$s0, $a0, $zero	#save Matrix 2 address
 	add	$s1, $a1, $zero	#save new matrix address
 	add	$s7, $a2, $zero	#Save MATIRIX SIZE argument
 	and	$t0, $t0, $zero	#initialize i = 0
 loopti:	
 	beq	$t0, $s7, exitti#branch if i = matrix size
 	and	$t1, $t1, $zero	#initialize j = 0
 looptj:	
 	beq	$t1, $s7, exittj#branch if j = matrix size
 	add	$s2, $s0, $zero	#reset matrix 2 address
 	add	$s3, $s1, $zero	#reset matrix 4 address
 	mult	$t0, $s7	#mult i * size
 	mflo	$t2		#get result
 	add	$t2, $t2, $t1	#add j
 	sll	$t2, $t2, 2	#multiply by 4
 	add	$s2, $s2, $t2	#add to marix 2
 	lw	$s4, 0($s2)	#get matrix 2 element
 	mult	$t1, $s7	#mult j * size
 	mflo	$t3		#get result
 	add	$t3, $t3, $t0	#add i
 	sll	$t3, $t3, 2	#multiply by 4
 	add	$s3, $s3, $t3	#add to marix 4 address
 	sw	$s4, 0($s3)	#store element in matrix 4
 	addi	$t1, $t1, 1	#j++
 	j looptj
 exittj:
 	addi	$t0, $t0, 1	#i++
 	j loopti
 exitti:
 	li	$v0, 0
      	jr	$ra
	

#####Transposed matrix multiply####
transmult:
	add	$s0, $a0, $zero	#save matrix 1
	add	$s1, $a1, $zero	#save matrix 4
	add	$s2, $a2, $zero	#save matrix 5
	add	$s7, $a3, $zero	#save size
	and	$t0, $t0, $zero	#initialize i
looptmi:
	beq	$t0, $s7, exittmi#branch if i = size
	and	$t1, $t1, $zero	#initialize j
looptmj:
	beq	$t1, $s7, exittmj#branch if j = size
	and	$t2, $t2, $zero	#initialize k
looptmk:
	beq	$t2, $s7, exittmk#branch if k = size
	add	$s3, $s0, $zero	#reset m1
	add	$s4, $s1, $zero	#reset m4
	add	$s5, $s2, $zero	#reset m5
	#Get the element from m1
	mult	$s7, $t0	#m = i * size
	mflo	$t3		#get result
	add	$t3, $t3, $t2	#m = m + k
	sll	$t3, $t3, 2	#shift left m
	add	$s3, $t3, $s3	#add m to m1 address
	lw	$t3, 0($s3)	#get m1 element
	#Get the element from m4
	mult	$s7, $t1	#p = j * size
	mflo	$t4		#get result
	add	$t4, $t4, $t2	#p = p + k
	sll	$t4, $t4, 2	#shift left p
	add	$s4, $t4, $s4	#add p to m4 address
	lw	$t4, 0($s4)	#get m4 element
	#get current element from m5
	mult	$t0, $s7	#n = i * size
	mflo	$t5		#get result
	add	$t5, $t5, $t1	#n = n + j
	sll	$t5, $t5, 2	#n = n * 4
	add	$s5, $s5, $t5	#add n to m5 address
	lw	$t5, 0($s5)	#get current m5 element
	#Add multiplied elements
	mult	$t3, $t4	#multiply m1 & m4 elements
	mflo	$t6		#get result
	add	$t5, $t5, $t6	#Add result to element value
	sw	$t5, 0($s5)	#store element to m5

	addi	$t2, $t2, 1	#k++
	j	looptmk
exittmk:
	addi	$t1, $t1, 1	#j++
	j	looptmj
exittmj:
	addi	$t0, $t0, 1	#i++
	j	looptmi
exittmi:
	li	$v0, 0
      	jr	$ra
