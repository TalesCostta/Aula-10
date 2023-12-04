import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

class NoAVL {
    int valor;
    int altura;
    NoAVL esquerda, direita;

    public NoAVL(int valor) {
        this.valor = valor;
        this.altura = 1;
    }
}

public class ArvoreAvl {
    private NoAVL raiz;

    // Retorna a altura de um nó
    private int altura(NoAVL no) {
        if (no == null) {
            return 0;
        }
        return no.altura;
    }

    // Retorna o fator de balanceamento de um nó
    private int fatorBalanceamento(NoAVL no) {
        if (no == null) {
            return 0;
        }
        return altura(no.esquerda) - altura(no.direita);
    }

    // Atualiza a altura de um nó
    private void atualizaAltura(NoAVL no) {
        if (no != null) {
            no.altura = 1 + Math.max(altura(no.esquerda), altura(no.direita));
        }
    }

    // Realiza uma rotação simples à direita
    private NoAVL rotacaoDireita(NoAVL y) {
        NoAVL x = y.esquerda;
        NoAVL T2 = x.direita;

        x.direita = y;
        y.esquerda = T2;

        atualizaAltura(y);
        atualizaAltura(x);

        return x;
    }

    // Realiza uma rotação simples à esquerda
    private NoAVL rotacaoEsquerda(NoAVL x) {
        NoAVL y = x.direita;
        NoAVL T2 = y.esquerda;

        y.esquerda = x;
        x.direita = T2;

        atualizaAltura(x);
        atualizaAltura(y);

        return y;
    }

    // Insere um valor na árvore AVL
    private NoAVL inserir(NoAVL no, int valor) {
        if (no == null) {
            return new NoAVL(valor);
        }

        if (valor < no.valor) {
            no.esquerda = inserir(no.esquerda, valor);
        } else if (valor > no.valor) {
            no.direita = inserir(no.direita, valor);
        } else {
            // Valores iguais não são permitidos em uma árvore AVL
            return no;
        }

        atualizaAltura(no);

        int balanceamento = fatorBalanceamento(no);

        // Casos de desequilíbrio
        // Esquerda-Esquerda
        if (balanceamento > 1 && valor < no.esquerda.valor) {
            return rotacaoDireita(no);
        }
        // Direita-Direita
        if (balanceamento < -1 && valor > no.direita.valor) {
            return rotacaoEsquerda(no);
        }
        // Esquerda-Direita
        if (balanceamento > 1 && valor > no.esquerda.valor) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }
        // Direita-Esquerda
        if (balanceamento < -1 && valor < no.direita.valor) {
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    // Método para ler valores de um arquivo e inserir na árvore
    public void inserirValoresDoArquivo(String caminhoDoArquivo) {
        try {
            long inicio = System.currentTimeMillis();

            BufferedReader leitor = new BufferedReader(new FileReader(new File(caminhoDoArquivo)));
            String linha = leitor.readLine(); // Lê a linha completa

            // Remove os colchetes e espaços em branco e quebra a string nos vírgulas
            String[] valores = linha.replaceAll("\\[|\\]|\\s", "").split(",");

            // Insere cada valor na árvore
            for (String valor : valores) {
                int num = Integer.parseInt(valor);
                raiz = inserir(raiz, num);
            }

            leitor.close();

            long fim = System.currentTimeMillis();
            long tempoDeExecucao = fim - inicio;

            // Imprime a árvore em ordem
            System.out.println("Árvore AVL em ordem:");
            imprimirEmOrdem(raiz);

            // Exibe o tempo de execução no final
            System.out.println("\nTempo de execução total: " + tempoDeExecucao + " milissegundos");
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    // Método de exemplo para imprimir a árvore em ordem
    public void imprimirEmOrdem(NoAVL no) {
        if (no != null) {
            imprimirEmOrdem(no.esquerda);
            System.out.print(no.valor + " ");
            imprimirEmOrdem(no.direita);
        }
    }

    public static void main(String[] args) {
        ArvoreAvl arvore = new ArvoreAvl();

        // Inserir valores a partir de um arquivo
        arvore.inserirValoresDoArquivo("./dados500_mil.txt");
    }
}
