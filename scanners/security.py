import subprocess

def run_bandit_scan():
    try:
        subprocess.run(["bandit", "-r", ".", "-f", "json", "-o", "repo_guardian_bot/reports/bandit.json"], check=True)
        return "Security issues report saved to reports/bandit.json"
    except Exception as e:
        return f"Bandit scan failed: {str(e)}"
