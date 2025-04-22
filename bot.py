import os
import platform
import subprocess

# Ensure reports directory exists
REPORT_DIR = "repo_guardian_bot/reports"
os.makedirs(REPORT_DIR, exist_ok=True)

def run_bandit_scan():
    try:
        subprocess.run([
            "bandit", "-r", ".", "-f", "json", "-o", f"{REPORT_DIR}/bandit.json"
        ], check=True)
        return f"Security issues report saved to {REPORT_DIR}/bandit.json"
    except Exception as e:
        return f"Bandit scan failed: {str(e)}"

def run_trufflehog_scan():
    try:
        with open(f"{REPORT_DIR}/trufflehog.json", "w") as f:
            subprocess.run([
                 "trufflehog", "filesystem", ".", "--json"
            ], stdout=f, check=True)
        return f"Secrets scan saved to {REPORT_DIR}/trufflehog.json"
    except Exception as e:
        return f"TruffleHog scan failed: {str(e)}"

def run_semgrep_scan():
    if platform.system() == "Windows":
        return "Semgrep skipped: not supported on Windows natively. Use WSL."
    try:
        subprocess.run([
            "semgrep", "scan", "--config=auto", "--json", "--output", f"{REPORT_DIR}/semgrep.json"
        ], check=True)
        return f"Static analysis saved to {REPORT_DIR}/semgrep.json"
    except Exception as e:
        return f"Semgrep scan failed: {str(e)}"

def main():
    print("[INFO] Running RepoGuardianBot scans...")
    bandit_results = run_bandit_scan()
    trufflehog_results = run_trufflehog_scan()
    semgrep_results = run_semgrep_scan()

    print("\n[SUMMARY] Scan Results")
    print("-" * 40)
    print("Bandit:", bandit_results)
    print("TruffleHog:", trufflehog_results)
    print("Semgrep:", semgrep_results)

if __name__ == "__main__":
    main()
