def gen_bigramm():
    bigramms = [
        "ст",
        "на",
        "но",
        "ен",
        "ко",
        "ни",
        "ра",
        "ов",
        "ро",
        "го",
        "ан",
        "то",
        "по",
        "од",
        "ер",
        "ре",
        "ос",
        "ор",
        "та",
        "пр",
        "ск",
        "ал",
        "во",
        "ол",
        "ом",
        "ва",
        "ны",
        "те",
        "ка",
        "ри",
        "ел",
        "от",
        "не",
        "он",
        "ес",
        "ти",
        "ве",
        "ин",
        "ет",
        "ог",
        "да",
        "ой",
        "ат",
        "ме",
        "ит",
        "де",
        "ки",
        "ар",
        "ис",
        "со",
    ]


    with open("russian_bigramm_statistic.txt", encoding="utf-8") as f:
        lines = f.read().split("\n")
    alphabet = lines[0].split('\t')[1:]   
    for i, l2 in enumerate(alphabet, 1):
        for line in lines[1:]:
            line = line.split('\t')
            bigramm = line[0] + l2
            if bigramm.lower() in bigramms:
                print(f'"{bigramm}" to {line[i]},')
                # dict[] = line[i] if line[i] != '' else 0




if __name__ == '__main__':
    gen_bigramm()
