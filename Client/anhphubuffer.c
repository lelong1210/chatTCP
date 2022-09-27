#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <signal.h>

#define FLAGSIZE_MAX 100

char flag[FLAGSIZE_MAX]; //Này là kích cỡ của file flag thôi nhá, đừng có lộn

void sigsegv_handler(int sig) {
  printf("%s\n", flag);
  fflush(stdout);
  exit(1);
}

void vuln(char *input){
  char buf2[64];
  strcpy(buf2, input);
}

int main(int argc, char **argv){
  
  FILE *f = fopen("flag.txt","r");
  if (f == NULL) {
    printf("%s %s", "Make a flag.txt if you want to test locally!\n");
    exit(0);
  }
  
  fgets(flag,FLAGSIZE_MAX,f);
  signal(SIGSEGV, sigsegv_handler);
  
  gid_t gid = getegid();
  setresgid(gid, gid, gid);

  int value;
  printf("Gõ anhphudeptrai để nhận flag nhé: ");
  fflush(stdout);
  char buf1[100];
  char str[] = "anhphudeptrai";
  gets(buf1); 
  vuln(buf1);

  value = strcmp(buf1, str);
  if(value == 0)
    printf("haha đéo có, bị lừa rồi!\n");
  else
    printf("bậy bạ, thử lại đê!\n");
  return 0;

}
