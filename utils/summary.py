import os

def generate_summary(report_dir):
    print("\n[Generated Summary Files]")
    for f in os.listdir(report_dir):
        print(" -", f)
