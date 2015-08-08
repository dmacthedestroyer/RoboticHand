load
#include "hcs12.inc"


;;Program Section

	ORG     $2000                   ; set pc to $2000

	LDS     #$2000                  ; initialize stack to $2000


	MOVB    #$9C,sci0bdl            ; set baud rate to 9600
	MOVB    #$0C,sci0cr2            ; enable tx, rx

	; Set up the ATD converter
	MOVB    #$80,atd0ctl2           ; power up ATD0
	MOVB    #$30,atd0ctl3           ; 6 conversions per sequence
	MOVB    #$EB,atd0ctl4           ; 8 bit resolution, 16 periods / conversion
	                                ; divide bus by 24 (1 MHz)
	                                
	MOVB    #$32,atd0ctl5           ; left-justified, unsigned, continuous
	                                ; multi-channel, channel 2 -> 3 -> 4 -> 5 -> 6 -> 7
	                                
        MOVB    #$80,atd1ctl2           ; power up ATD1
	MOVB    #$10,atd1ctl3           ; 2 conversions per sequence
	MOVB    #$EB,atd1ctl4           ; 8 bit resolution, 16 periods / conversion
	                                ; divide bus by 24 (1 MHz)
					
	MOVB    #$32,atd1ctl5            ; left-justified, unsigned, continuous
	                                ; multi-channel, channel 2 -> 3

;;configure PWM block 
	MOVB    #$FF,pwmpol             ; pulse high on block 0-7
	MOVB    #$11,pwmprclk           ; prescale clock tap
	MOVB    #0,pwmscla              ; scale clock by this amount
	MOVB    #0,pwmsclb              ; clock b scale select
	MOVB    #$FF,pwmclk             ; select prescaled or scaled clock for 0 - 5
	
	MOVB    #255,pwmper0            ; set period for channel 0
	MOVB    #30,pwmdty0             ; set duty cycle for channel 0
	
	MOVB    #255,pwmper1            ; set period for channel 1
	MOVB    #30,pwmdty1             ; set duty cycle for channel 1
	
	MOVB    #255,pwmper2            ; set period for channel 2
	MOVB    #30,pwmdty2             ; set duty cycle for channel 2
	
	MOVB    #255,pwmper3            ; set period for channel 3
	MOVB    #30,pwmdty3             ; set duty cycle for channel 3
	
	MOVB    #255,pwmper4            ; set period for channel 4
	MOVB    #30,pwmdty4             ; set duty cycle for channel 4
	
	MOVB    #255,pwmper5            ; set period for channel 5
	MOVB    #30,pwmdty5             ; set duty cycle for channel 5
	
	MOVB    #255,pwmper6            ; set period for channel 6
	MOVB    #30,pwmdty6             ; set duty cycle for channel 6
	
	MOVB    #255,pwmper7            ; set period for channel 7
	MOVB    #30,pwmdty7             ; set duty cycle for channel 7

	MOVB    #$FF,pwme               ; enable channels 0 - 7
	
;;start main program 	
start:
	JSR     GETCSCI0		; check for input
	CMPA    #0			; compare to start char
	BEQ     loop			; begin main loop if we receive start char
	BRA     start			; else loop until start char is received
	
; Main loop
loop:
	;Pot and servo0
	LDAA    ATD0DR0                 ; load atd result reg 0
	STAA    oData0                 	; store result at oData0
	JSR     PUTC2SCI0               ; send result out over SCI0

	JSR     GETCSCI0                ; read incoming data
	
	STAA    inData0                 ; store at inData
        STAA    pwmdty0                 ; Send Dmac number to Servo 0


	;pot and servo1
	LDAA    ATD0DR1                 ; load
	STAA    oData1                  ; store result at oData1
	JSR     PUTC2SCI0
	
	JSR     GETCSCI0                 ; read incoming data

	STAA    inData1                 ; store at inData1
	STAA    pwmdty1                 ; send Dmac number to servo 1
	
	
	;pot and servo2
	
	LDAA    ATD0DR2                 ; load
	STAA    oData2                  ; store result at oData2
	JSR     PUTC2SCI0

	JSR     GETCSCI0                 ; read incoming data

	STAA    inData2                 ; store at inData2
	STAA    pwmdty2                 ; send Dmac number to servo 2
	
	
	;pot and servo3
	LDAA    ATD0DR3                 ; load
	STAA    oData3                  ; store result at oData3
	JSR     PUTC2SCI0

	JSR     GETCSCI0                 ; read incoming data

	STAA    inData3                 ; store at inData3
	STAA    pwmdty3                 ; send Dmac number to servo 3
	
	
	;pot and servo4
	LDAA    ATD0DR4                 ; load
	STAA    oData4                  ; store result at oData4
	JSR     PUTC2SCI0

	JSR     GETCSCI0                 ; read incoming data

	STAA    inData4                 ; store at inData4
	STAA    pwmdty4                 ; send Dmac number to servo 4
	
	
	;pot and servo5
	LDAA    ATD0DR5                 ; load
	STAA    oData5                  ; store result at oData5
	JSR     PUTC2SCI0

	JSR     GETCSCI0                 ; read incoming data

	STAA    inData5                 ; store at inData5
	STAA    pwmdty5                 ; send Dmac number to servo 5
	
	
	;pot and servo6
	LDAA    ATD1DR0                 ; load
	STAA    oData6                  ; store result at oData5
	JSR     PUTC2SCI0

	JSR     GETCSCI0                 ; read incoming data

	STAA    inData6                 ; store at inData5
	STAA    pwmdty6                 ; send Dmac number to servo 6
	
	
	;pot and servo7
	LDAA    ATD1DR1                 ; load
	STAA    oData7                  ; store result at oData5
	JSR     PUTC2SCI0

	JSR     GETCSCI0                 ; read incoming data

	STAA    inData7                 ; store at inData5
        STAA    pwmdty7                 ; send Dmac number to servo 7
        
	JMP     loop                    ; loop

	SWI

;;Subroutine Section

;PUTC, GETC subroutines based on Huang's HCS12 textbook code

;Subroutine PUTC2SCI0: outputs a character to SCI0 using polling method
PUTC2SCI0:
	BRCLR	SCI0SR1,TDRE,*  	; wait for TDRE set
	STAA	SCI0DRL         	; write to SCI0DRL
	RTS

;Subroutine GETCSCI0: reads a character from SCI0 using polling method
GETCSCI0:
	BRCLR   SCI0SR1,RDRF,*  	; wait for RDRF set
	LDAA    SCI0DRL                 ; read the character
	CMPA    #255			; check for break char
	BNE     pass			
	JMP     start
pass	RTS

;Subroutine DELAY: delays for a # of 1msec loops
DELAY:
	PSHY
	PSHX
	LDY     #$02                    ; this controls # of 1 msec loops
loop1:	LDX     #$1770
loop2:  DEX
	BNE     loop2
	DEY
	BNE     loop1
	PULX
	PULY
	RTS

;;Storage
	ORG     $1000

; currently, the storage is only allotted for one byte
oData0	RMB     $00                     ; storage for data output 0
oData1	RMB     $00                     ; storage for data output 1
oData2	RMB     $00                     ; storage for data output 2
oData3	RMB     $00                     ; storage for data output 3
oData4	RMB     $00                     ; storage for data output 4
oData5  RMB     $00                     ; storage for data output 5
oData6  RMB     $00                     ; storage for data output 6
oData7  RMB     $00                     ; storage for data output 7

inData0 RMB     $00                     ; storage for data received 0
inData1 RMB     $00                     ; storage for data received 1
inData2 RMB     $00                     ; storage for data received 2
inData3 RMB     $00                     ; storage for data received 3
inData4 RMB     $00                     ; storage for data received 4
inData5 RMB     $00                     ; storage for data received 5
inData6 RMB     $00                     ; storage for data received 6
inData7 RMB     $00                     ; storage for data received 7

	END